data <- read.table("E:/Dropbox/B3/e-Commerce/Application_DataMining/R/Exercice6.2/Eaux1.txt" , sep="" ,header=T , row.names=1)
#installer package library(FactoMineR)
library(FactoMineR)
acp <- PCA(data,quali.sup=6)
acp$eig

barplot(acp$eig[,2] , names=paste("d",1:nrow(acp$eig)))