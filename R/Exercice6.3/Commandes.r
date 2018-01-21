data <- read.table("C:/Users/Doublon/Desktop/R/Exercice6.3/USC.csv" , sep=";" ,header=T , row.names=1)
library(FactoMineR)
afc <- CA(data)
afc
#p-value =  5.763222e-56 donc petite , donc on rejete H0 donc pas de lien entre l'origine sociale et l'orientation d'études
afc$eig

#####
data <- read.table("C:/Users/Doublon/Desktop/R/Exercice6.3/etude-agro-mais.csv" , sep=";" ,header=T , row.names=1)
library(FactoMineR)

#je prend les colonnes qui m'interesse et je fais un vecteur avec 
lignedata=data[,c(5,7)]
lignedata

#je "transforme" ces données quali en quanti(nombre)
etudeAFC<-table(lignedata$Couleur,lignedata$Enracinement)
etudeAFC

#p-value =  0.006326367 donc on garde l'hypothese H0 donc il y bien un lien entre la couleur et l'enracinement
afc <- CA(etudeAFC)
afc

testchi <- chisq.test(etudeAFC)
testchi$residuals