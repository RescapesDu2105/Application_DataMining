data <- read.table("C:/Users/Doublon/Desktop/R/Exercice6.4/lentilles.csv" , sep=";" ,header=T , row.names=1)
data
library(FactoMineR)
td<-tab.disjonctif(data)
td
acm <- MCA(data)
dimdesc(acm)