	ORG $4000
	ABA
	ADDD 1,X
PUERTA	EQU $1000
	ADDD 2,X
	ADDD -2,X
	ADDD -16,PC
	ADDD 200,SP
	ADDD -200,SP
	ADDD [45,PC]
	ADDD [-45,PC]
	ADDD [256,PC]
	ADDD [45,PC]
	ADDD [D,PC]
	ADDD [A,PC]
	ADDD D,PC
	ADDD A,PC
FIN2	END