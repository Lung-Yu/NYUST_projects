from matplotlib import pyplot as plt
import mini_project as mini
import csv


class PlotChart:

	def __init__(self,title='Home Work Gaussian'):
		plt.xlabel("x-axis")
	 	plt.ylabel("y-axis")
	 	plt.grid(True)
		plt.title(title)
		self._colors = []
		self._games = []
		self._legend = []
		self.Threshold = 0

	def setPredict(self,predict):
		self._predict = predict

	def addGaussian(self,game,color='r',legend='sample'):
		self._games.append(game)
		self._colors.append(color)
		self._legend.append(legend)
		plt.plot(game.statistics,color)
		
	def _setXlim(self):
		maxVal = 0
		for game in self._games :
			maxVal = max(len(game.statistics),maxVal)
		plt.xlim(0,maxVal)

	def _setYlim(self):
		maxVal = 0
		for game in self._games :
			maxVal = max(game.maxPositiveCount,maxVal)
		plt.ylim(0,maxVal)

	def _plotPredict(self):
		self.classfiyIndex = 0
		for i in range(0,len(self._predict)):
			gameIndex = self._predict[i]
			maxVal = 0
			for j in range(len(self._games)) :
				if self._games[j].statistics[i] > maxVal :
					maxVal = self._games[j].statistics[i]
					self.Threshold = i
					self.classfiyIndex = j
					#print ("%d,%d")%(self.classfiyIndex,self.Threshold)
			color = self._colors[gameIndex]
			#print ("index %d , game : %d ,max = %d ,color = %s")%(i,gameIndex,maxVal,color)
			plt.plot([i,i],[0,maxVal],color)
	
	def getThreshold(self):
		return self.Threshold

	def show(self):
		self._setXlim()
		self._setYlim()
		self._plotPredict()
		plt.legend(self._legend, loc='upper left')
		plt.show()


class ConfusionMatrix:
	def __init__(self,games,threshold,D=100,title='Game vs Game',Inverse=False,IsAddExportBaysianTitle = True):
		self._games = games
		self._threshold = threshold
		self._Size = D
		self._title = title
		self._gameSize = len(self._games)
		self._Inverse = Inverse
		self._IsAddExportBaysianTitle = IsAddExportBaysianTitle
		self._run()

	def _run(self):
		self._initConfusionMatrix()
		self._calc()
		self._InverseConfusionMatrix()

	def _calc(self):
		for i in range(0,self._Size):
			for j in range(0,self._gameSize):
				if i > self._threshold :
					for j in range(0,self._gameSize):
						val = self._games[j].statistics[i]
						if j == min(i-self._threshold,0) :
							self._fp = self._fp + val
						else:
							self._tp = self._tp + val
				else :
					for j in range(0,self._gameSize):
						val = self._games[j].statistics[i]
						if j != min(i-self._threshold,0) :
							self._tn = self._tn + val
						else:
							self._fn = self._fn + val
		

	def _initConfusionMatrix(self):
		self._tp = 0
		self._tn = 0
		self._fp = 0
		self._fn = 0
	def _formateConfusionMatrix(self):
		self._tp = self._tp/self._gameSize
		self._tn = self._tn/self._gameSize
		self._fp = self._fp/self._gameSize
		self._fn = self._fn/self._gameSize
	def _InverseConfusionMatrix(self):
		#print "_InverseConfusionMatrix :"+ str(self._Inverse)
		if self._Inverse :
			# tp = self._tp
			# self._tp = self._tn
			# self._tn = tp

			# fp = self._fp 
			# self._fp = self._fn 
			# self._fn = fp
			tp = self._tp 
			self._tp = self._fp 
			self._fp = tp

	def getSensitivity(self):
		if (self._tp + self._fn) == 0:
			return 0
		return float(self._tp) / (self._tp + self._fn)
	def getSpecificity(self):
		if (self._tn + self._fp == 0):
			return 0
		return float(self._tn) / (self._tn + self._fp)

	def getFalsePositiveRate(self):
		if (self._tn + self._fp) == 0:
			return 0
		return float(self._fp) / (self._tn + self._fp)
	def getFaseNegativeRate(self):
		if (self._tp + self._fn) == 0:
			return 0
		return float(self._fn) / (self._tp + self._fn)
	def getAccuary(self):
		if (self._tp + self._fp + self._tn + self._fn) == 0:
			return 0
		return float(self._tp + self._tn) / (self._tp + self._fp + self._tn + self._fn)

	def getTrueSensitivity(self):
		if (self._tp + self._tn) == 0:
			return 0
		return float(self._tp) / (self._tp + self._tn)
		
	def getFalseSensitivity(self):
		if (self._tp + self._tn) == 0:
			return 0
		return float(self._fn) / (self._tp + self._tn)
		
	def getTrueSpecificity(self):
		if (self._tn+self._fp) == 0:
			return 0
		return float(self._tn) / (self._tn+self._fp)
		
	def getFalseSpecificity(self):
		if (self._tn + self._fp) == 0:
			return 0
		return float(self._fp) / (self._tn + self._fp)
		
	def PositivePredictedValue(self):
		if (self._tp + self._fp) == 0:
			return 0
		return float(self._tp) / (self._tp + self._fp)
		
	def NegativePredictedValue(self):
		if (self._tn + self._fn) :
			return 0
		return float(self._tn) / (self._tn + self._fn)
		
	def show(self):
		count = len(self._games)
		print "**********************"
		print self._title
		print "Threshold : " + str(self._threshold)
		print "**********************"
		print ("TP : %d , TN : %d")%(self._tp/count, self._tn/count)
		print ("FP : %d , FN : %d")%(self._fp/count, self._fn/count)
		print ("TPR(Sensitivity) : %f"% self.getSensitivity())
		#print ("Specificity : %f"% self.getSpecificity())
		print ("FPR(1-Specificity) : %f"% (1-self.getSpecificity()))
		#print ("FPR : %f"% self.getFalsePositiveRate())
		#print ("FNR : %f"% self.getFaseNegativeRate())
		print ("Accuracy : %f"% self.getAccuary())
		print "**********************"
		
		# print ("*Sensitivity : %f"% self.getTrueSensitivity())
		# print ("*Specificity : %f"% self.getFalseSensitivity())
		# print ("PPV : %f"% self.PositivePredictedValue())
		# #print ("NPV : %d"% self.NegativePredictedValue())
		# print "**********************"
		print ""

	def writeBayesian(self,title):
		print "export Bayesian... "
		with open('Bayesian.csv', 'a') as csvfile:
			fieldnames = ['title','TPR', 'FPR','Accuary']
			writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
			if self._IsAddExportBaysianTitle :
				writer.writeheader()
			writer.writerow({'title':title,'TPR':self.getSensitivity(),"FPR":(1-self.getSpecificity()), 'Accuary': self.getAccuary()})


	def exportCSV(self,save_file_name):
		print ("export %s... ")%save_file_name
		with open(save_file_name, 'wb') as csvfile:
			fieldnames = ['TPR', 'FPR','Accuary']
			writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
			writer.writeheader()
			for threshold in range(0,100):
				self._threshold = threshold
				self._run()
				#self.show()
				# if self.getSensitivity() >= 1:
				#  	continue
				if self.getFalsePositiveRate() == 0:
					writer.writerow({'TPR':self.getSensitivity(),"FPR":(1-self.getSpecificity()), 'Accuary': self.getAccuary()})
					pass
				else:
					writer.writerow({'TPR':self.getSensitivity(),"FPR":(1-self.getSpecificity()), 'Accuary': self.getAccuary()})
			

		