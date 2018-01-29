data <- read.table("E:/Dropbox/B3/e-Commerce/Application_DataMining/R/Exercice6.1/taches_menageres.txt" , sep="" ,header=T , row.names=1)
tabconting <- matrix(c(data$Wife , data$Alternating , data$Husband , data$Jointly),ncol=4)

rownames(tabconting) <-  c(rownames(data))
colnames(tabconting) <-  c(colnames(data))

par(mfrow=c(4,1))
barplot(tabconting[1,], main="Laundry")
barplot(tabconting[2,], main="Main_meal")
barplot(tabconting[3,], main="Dinner")
barplot(tabconting[4,], main="Breakfeast")
barplot(tabconting[5,], main="Tidying")
barplot(tabconting[6,], main="Dishes")
barplot(tabconting[7,], main="Shopping")
barplot(tabconting[8,], main="Official")
barplot(tabconting[9,], main="Driving")
barplot(tabconting[10,], main="Finances")
barplot(tabconting[11,], main="Insurance")
barplot(tabconting[12,], main="Repairs")
barplot(tabconting[13,], main="Holidays")

chisq.test(tabconting)

############
data <- read.table("E:/Dropbox/B3/e-Commerce/Application_DataMining/R/Exercice6.1/salaires.txt" , sep="" ,header=T)
tabconting <- matrix(c(data$minority , data$sex),ncol=2)

rownames(tabconting) <-  c(rownames(data))
colnames(tabconting) <-  c("minority","sex")

chisq.test(tabconting)