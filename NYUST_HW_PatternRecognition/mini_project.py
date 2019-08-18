import random
import numpy as np
from matplotlib import pyplot as plt
import math

class ThrowCoins:

    def __init__(self,threshold):
        self.threshold = threshold
        self.maxPositiveCount = 0
        self._countAmount = 0
        self._record = []
        self.playCount = 0 
        self.statistics = []

    def getRecord(self):
        return self._record

    def play(self,time):
        self.unitTime = time
        self._scaleAdjustmentVector()
        self._playGame()
        self._calcPlotInfo()

    def getPositiveAmount(self):
        return self._countAmount

    def _playGame(self):
        self.currentPositiveCount = self._calcPositiveCount(self.unitTime)
        self._statisticsAdd(self.currentPositiveCount)

    def _calcPlotInfo(self):
        self._calcMaxPositive(self.currentPositiveCount)
        self._countAmount = self._countAmount + self.currentPositiveCount
        self._playCounterAdd()

    def _playCounterAdd(self):
        self.playCount = self.playCount + 1

    def _calcPositiveCount(self,time):
        count = 0
        for i in range(0,time):
            if self._isPositive() :
                count = count + 1
                self._record.append(1)
            else :
                self._record.append(0)

        return count

    def _scaleAdjustmentVector(self):
        if len(self.statistics) < self.unitTime :
            for i in range(-1,self.unitTime):
                self.statistics.append(0)

    def _statisticsAdd(self,index):
        self.statistics[index] = self.statistics[index] + 1

    def _calcMaxPositive(self,currentCount) :
        if self.maxPositiveCount < self.statistics[currentCount] :
            self.maxPositiveCount = self.statistics[currentCount]

    def _isPositive(self):
        val = random.random()
        return self.threshold > val
 
class Player:
    def __init__(self):
        self.time = 100
        self.unitTime = 100
        self.threshold = 0.5

    def __init__(self,threshold):
        self.time = 100
        self.unitTime = 100
        self.threshold = threshold

    def play(self):
        self.game = ThrowCoins(self.threshold)
        for i in range(0,self.time) :
            self.game.play(self.unitTime)

class ConfusionMatrix:
    def __init__(self,game,unitTime):
        self.game = game
        self.threshold = game.threshold
        self.time = game.playCount
        self.unitTime = unitTime

    def totalTime(self):
        return self.time * self.unitTime

    def predictPositive(self):
        return int(((1 - self.threshold) * self.totalTime()))
    
    def predictNegative(self):
        return int(self.threshold * self.totalTime())

    def actualPositive(self):
        return int(self.game.getPositiveAmount())

    def actualNegative(self):
        return int(self.time * self.unitTime - self.game.getPositiveAmount())

    def TP(self):
        return min(self.predictPositive() , self.actualPositive())

    def TN(self):
        return min(self.predictNegative() , self.actualNegative())

    def FP(self):
        return abs(min(self.predictPositive() - self.actualPositive(),0))

    def FN(self):
        return abs(min(self.predictNegative() - self.actualNegative(),0))

def main():
    game1 = playgame(0.25)
    game2 = playgame(0.5)
    plotGaussian(game1,game2)

def plotGaussian(game1,game2):
    plt.plot(game1.statistics,'b') 
    plt.plot(game2.statistics,'g')
    plt.xlim(0,max(len(game1.statistics),len(game2.statistics)))
    plt.ylim(0,max(game1.maxPositiveCount,game2.maxPositiveCount))
    plt.grid(True)
    plt.xlabel("x-axis") 
    plt.ylabel("y-axis") 
    #plt.text(50, 50, r'$\mu=100,\ \sigma=15$')
    plt.title("Home Work Gaussian") 
    plt.show() 




def playgame(threshold):
    play = Player(threshold)
    play.time = 1000
    play.unitTime = 100
    play.play()

    name = 'T : ' + str(threshold)
    showConfusionMatrix(name,play)
    return play.game

def playgame(threshold,times):
    play = Player(threshold)
    play.time = times
    play.unitTime = 100
    play.play()

    return play.game

def showConfusionMatrix(playName,play):

    confusionMatrix = ConfusionMatrix(play.game,play.unitTime)

    print '--------------- '+playName+' --------------------'
    print 'predictPositive ',confusionMatrix.predictPositive()
    print 'predictNegative ',confusionMatrix.predictNegative()
    print 'actualPositive ', confusionMatrix.actualPositive()
    print 'actualNegative ', confusionMatrix.actualNegative()
    print ''
    print 'TP ' , confusionMatrix.TP()
    print 'TN ' , confusionMatrix.TN()
    print 'FP ' , confusionMatrix.FP()
    print 'FN ' , confusionMatrix.FN()
    print '--------------------------------------------'
    print ''

if __name__ == '__main__':
    main()