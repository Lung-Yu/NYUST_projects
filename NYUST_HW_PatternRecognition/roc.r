setwd('C:/Users/lungyu/Documents/GitHub/PatternRecognition')

mydata0 <- read.csv('pattern_period_project_roc_data_0.csv')
mydata1 <- read.csv('pattern_period_project_roc_data_1.csv')
mydata2 <- read.csv('pattern_period_project_roc_data_2.csv')
mydata3 <- read.csv('pattern_period_project_roc_data_3.csv')

datas <- c(mydata0,mydata1,mydata2,mydata3)
bayesianData <- read.csv('Bayesian.csv')

#define basic roc 
xrange <- cbind(0,1)
yrange <- cbind(0,1)
plot(xrange,yrange,xlab='FPV(1 - Specificity)',ylab='TPR (Sensitivity)',title('ROC curve'))

# base line
lines(xrange,yrange,type = "l" ,col="gray",lwd=2) 


# this data roc curve
lines(x=mydata0$FPR ,y=mydata0$TPR,type="l",col="#880000",lwd=2)
lines(x=mydata1$FPR ,y=mydata1$TPR,type="l",col="#33FFFF",lwd=2)
lines(x=mydata2$FPR ,y=mydata2$TPR,type="l",col="#7744FF",lwd=2)
lines(x=mydata3$FPR ,y=mydata3$TPR,type="l",col="#B94FFF",lwd=2)

# bayes thory point 
points(x=bayesianData$FPR,y=bayesianData$TPR,col="red",bg = "grey",pch = 19)

  
