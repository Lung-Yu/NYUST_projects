def GetRates(actives, scores):
    tpr = [0.0]  # true positive rate
    fpr = [0.0]  # false positive rate
    nractives = len(actives)
    nrdecoys = len(scores) - len(actives)

    foundactives = 0.0
    founddecoys = 0.0
    for idx, (id, score) in enumerate(scores):
        if id in actives:
            foundactives += 1.0
        else:
            founddecoys += 1.0
        tpr.append(foundactives / float(nractives))
        fpr.append(founddecoys / float(nrdecoys))
    return tpr, fpr
    
def DepictROCCurve(actives, scores, label, color, fname, randomline=True):
    plt.figure(figsize=(4, 4), dpi=80)
    SetupROCCurvePlot(plt)
    AddROCCurve(plt, actives, scores, color, label)
    SaveROCCurvePlot(plt, fname, randomline)

def SetupROCCurvePlot(plt):
    plt.xlabel("FPR", fontsize=14)
    plt.ylabel("TPR", fontsize=14)
    plt.title("ROC Curve", fontsize=14)

def AddROCCurve(plt, actives, scores, color, label):
    tpr, fpr = GetRates(actives, scores)
    plt.plot(fpr, tpr, color=color, linewidth=2, label=label)

def SaveROCCurvePlot(plt, fname, randomline=True):
    if randomline:
        x = [0.0, 1.0]
        plt.plot(x, x, linestyle='dashed', color='red', linewidth=2, label='random')
    plt.xlim(0.0, 1.0)
    plt.ylim(0.0, 1.0)
    plt.legend(fontsize=10, loc='best')
    plt.tight_layout()
    plt.savefig(fname)