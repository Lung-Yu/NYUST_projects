import mini_project as mini
import plot_chart as myPloter
from matplotlib import pyplot as plt

ThetaA_Time = 6000
ThetaB_Time = 4000
ThetaC_Time = 10000
D = 100

def twoCoins(ThetaA = 0.56,ThetaB = 0.38):
	game1 = mini.playgame(ThetaA,ThetaA_Time)
	game2 = mini.playgame(ThetaB,ThetaB_Time)
	(predicts,t) = getPredict2(ThetaA, ThetaB)

	cm = myPloter.ConfusionMatrix([game1,game2],t,Inverse=False)
	#cm = myPloter.ConfusionMatrix([game1,game2],50)
	cm.show()
	cm.writeBayesian('Two Coins')
	cm.exportCSV('pattern_period_project_roc_data_0.csv')

	ploter = myPloter.PlotChart('Two Coins Gaussian')
	ploter.addGaussian(game1,color='b',legend='Theta = %s'%ThetaA)
	ploter.addGaussian(game2,color='r',legend='Theta = %s'%ThetaB)
	ploter.setPredict(predicts)
	ploter.show()

	

def bayesian2(thetaA,thetaB,num):
	p_theta_a = ( pow(thetaA,num) * pow(1-thetaA,D-num) * thetaA ) / ( (pow(thetaA,num) * pow(thetaA,D-num) * thetaA) + (pow(thetaB,num)*pow(1-thetaB,D-num)*thetaB) )
	p_theta_b = ( pow(thetaB,num) * pow(1-thetaB,D-num) * thetaA ) / ( (pow(thetaA,num) * pow(thetaA,D-num) * thetaA) + (pow(thetaB,num)*pow(1-thetaB,D-num)*thetaB) )

	if p_theta_a > p_theta_b :
		return thetaA
	else: 
		return thetaB
def getPredict2(ThetaA,ThetaB):
	count = []
	t = -1
	for i in range(0,D):
		val = bayesian2(ThetaA, ThetaB , i)
		if val == ThetaA :
			count.append(0)
			if (i!=0) and count[i-1]!=0:
				t = i
		else :
			count.append(1)
			if (i!=0) and count[i-1]!=1:
				t = i
	return (count,t)

def threeCoins(ThetaA = 0.5,ThetaB = 0.75,ThetaC = 0.33):
	ploter = myPloter.PlotChart('Three Coins Gaussian')
	game1 = mini.playgame(ThetaA,ThetaA_Time)
	game2 = mini.playgame(ThetaB,ThetaB_Time)
	game3 = mini.playgame(ThetaC,ThetaC_Time) 

	(predicts,ts) = getPredict3(ThetaA, ThetaB,ThetaC)

	cm = myPloter.ConfusionMatrix([game1,game2,game3] ,ts[0]	,title='Is Game1(red)',IsAddExportBaysianTitle=False)
	cm.show()
	cm.writeBayesian('Is Game1(red)')
	cm.exportCSV('pattern_period_project_roc_data_1.csv')

	cm = myPloter.ConfusionMatrix([game2,game3,game1] ,ts[1]	,title='Is Game2(green)',Inverse=True,IsAddExportBaysianTitle=False)
	cm.show()
	cm.writeBayesian('Is Game2(green)')
	cm.exportCSV('pattern_period_project_roc_data_2.csv')

	cm = myPloter.ConfusionMatrix([game3,game2,game1] ,ts[0]	,title='Is Game3(blue)',IsAddExportBaysianTitle=False)
	cm.show()
	cm.writeBayesian('Is Game3(blue)')
	cm.exportCSV('pattern_period_project_roc_data_3.csv')
	
	ploter.addGaussian(game1,color='r', legend='Theta = %s'%ThetaA)
	ploter.addGaussian(game2 ,color='g', legend='Theta = %s'%ThetaB)
	ploter.addGaussian(game3 ,color='b', legend='Theta = %s'%ThetaC)
	ploter.setPredict(predicts)
	ploter.show()

def bayesian3(thetaA,thetaB,thetaC,num):
	p_theta_a = ( pow(thetaA,num) * pow(1-thetaA,D-num) * thetaA ) / ( (pow(thetaA,num) * pow(thetaA,D-num) * thetaA) + (pow(thetaB,num)*pow(1-thetaB,D-num)*thetaB) + (pow(thetaC,num)*pow(1-thetaC,D-num)*thetaC))
	p_theta_b = ( pow(thetaB,num) * pow(1-thetaB,D-num) * thetaB ) / ( (pow(thetaA,num) * pow(thetaA,D-num) * thetaA) + (pow(thetaB,num)*pow(1-thetaB,D-num)*thetaB) + (pow(thetaC,num)*pow(1-thetaC,D-num)*thetaC))
	p_theta_c = ( pow(thetaC,num) * pow(1-thetaC,D-num) * thetaC ) / ( (pow(thetaA,num) * pow(thetaA,D-num) * thetaA) + (pow(thetaB,num)*pow(1-thetaB,D-num)*thetaB) + (pow(thetaC,num)*pow(1-thetaC,D-num)*thetaC))

	if (p_theta_a > p_theta_b) and (p_theta_a > p_theta_c):
		return thetaA
	elif (p_theta_b > p_theta_a) and (p_theta_b > p_theta_c):
		return thetaB
	else:
		return thetaC

def getPredict3(ThetaA,ThetaB,ThetaC):
	count = []
	t = []

	for i in range(0,D):
		val = bayesian3(ThetaA, ThetaB,ThetaC , i)
		if val == ThetaA :
			count.append(0)
			if (i!=0) and count[i-1]!=0:
				t.append(i)

		elif val == ThetaB :
			count.append(1)
			if (i!=0) and count[i-1]!=1:
				t.append(i)
		else :
			count.append(2)
			if (i!=0) and count[i-1]!=2:
				t.append(i)

	print(t)
	return (count,t)

def main():
	#twoCoins(ThetaA=0.2,ThetaB=0.8)
	twoCoins(ThetaA=0.38,ThetaB=0.56)
	#twoCoins()
	threeCoins()

if __name__ == '__main__':
	main()

