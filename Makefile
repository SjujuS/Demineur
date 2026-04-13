### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS = 

### REGLES ESSENTIELLES ###

Main.class: Main.java Accueil.class ActionAccueil.class EcranChoix.class EcranJeu.class Mines.class Fenetre.class Carre.class CreateButton.class Sauvegarde.class
	$(JC) $(JCFLAGS) Main.java

Accueil.class: Accueil.java Fenetre.class CreateButton.class ActionAccueil.class Header.class
	$(JC) $(JCFLAGS) Accueil.java

ActionAccueil.class: ActionAccueil.java Fenetre.class EcranChoix.class
	$(JC) $(JCFLAGS) ActionAccueil.java

EcranChoix.class: EcranChoix.java Fenetre.class CreateButton.class Header.class
	$(JC) $(JCFLAGS) EcranChoix.java

EcranJeu.class: EcranJeu.java Fenetre.class Carre.class CreateButton.class Mines.class Header.class
	$(JC) $(JCFLAGS) EcranJeu.java

Fenetre.class: Fenetre.java
	$(JC) $(JCFLAGS) Fenetre.java

Carre.class: Carre.java
	$(JC) $(JCFLAGS) Carre.java

CreateButton.class: CreateButton.java
	$(JC) $(JCFLAGS) CreateButton.java

Mines.class : Mines.java EcranChoix.class
	$(JC) $(JCFLAGS) Mines.java

Header.class: Header.java
	$(JC) $(JCFLAGS) Header.java

Sauvegarde.class: Sauvegarde.java
	$(JC) $(JCFLAGS) Sauvegarde.java
### REGLES OPTIONNELLES ###

run: Main.class
	$(JVM) $(JVMFLAGS) Main

clean:
	rm -f *.class

mrproper: clean Main.class

.PHONY: run clean mrproper