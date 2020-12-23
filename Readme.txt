
########   Introduction
This program is written in java language and  aims to prioritize test cases using genetic algorithm and hill climbing with respect to APTC objective metric.



######## Running instruction
APTC accepts four input paremeter, namely dataCoverage input file name, parent selector coding,
crossover operator coding, mutation operator coding. These inputs enable users to choose any
combination of the operators to run APTC.

However, it is possible to run the APTC with only one single input [data coverage file name ]

If APTC receive only data coverage file name, it uses the best permutation of operators, as default combination.
Considering these, we have two types of calling which wil be expalined at the end of "compile section"

####Execution
There is APTC.jar in the \APTC\out\artifacts\APTC_jar, which can be used to execute the project.


##### Compile Instruction
The project files are available for standalone execution
The project can be complied through either IntelliJ IDEA or Eclipse IDE.
If there is no IDE available the following steps are required to compile and execute the project.


Inorder to compile and execute via command prompt , follow this steps

Step#1 move to project directory: the name of project folder is APTC

Step#2 execute the below command to compile the project and move .class files in the bin folder
javac -sourcepath src -d bin  src/com/*.java

Step#3 move to the outputTSP folder
cd ./bin

step#4 execute the below command to execute main class
java  com.csi.TCPrioritization [datasetfileName] [parentSelectorCode] [CrossOverTypeCode] [MutationTypeCode]
OR
java  com.csi.TCPrioritization [datasetfileName]

Mutation operators:
0: Inseration mutation
1: Exchange mutation
2: Displacement mutation

CrossOver operators:
0: PartiallyMapped Crossover
1: Order Crossover
2: Maximal presentative Crossover
3: PositionBased Crossover

Parent selectors:
0: RouletteWheelLinearRanking
1: Tournament

#### all parameter can be moified in the Coniguration.java class
These are the default parameters which we have considered
Parameters	                                Values
Population Size	                            100
Crossover rate	                            0.8
Mutation rate	                            0.05 for large data set, and 1/size of chromosomes (test suites) for small dataset
Elitism	                                    1
Termination condition	                    Execution time = 1 minute
Tournament size	                            2
S value in linear ranking selection     	1.5




***********************RUNNING RANDOM RESTART HIlL CLIMBING
In the TCPrioritization class( here TCPrioritization.java class is the main class), you should
comment lines 40 and 41 (calling genetic algorithm) and  uncomment lines 50 and 51(calling hill climbing)

*********** RUNNING RANDOM SEARCH

 In the TCPrioritization class( here TCPrioritization.java class is the main class), you should
  comment lines 40 and 41 (calling genetic algorithm) and  uncomment lines 45 and 46(calling random Search)

***********CREATE SYNTHESIZED DATASET

In the TCPrioritization class( here TCPrioritization.java class is the main class), you should
comment lines 40 and 41 (calling genetic algorithm) and  uncomment lines 57 and 58(calling )

Class GenerateRandomizedDataSet is responsible for creating synthesized data
There are three variable: 
countItems :  is used for test cases counts
maxCountDetail : defines how much test point each test case can cover
maxValueDetail : maximum number of test points

by increasing max count detail when the two other variables are kept fixed, we increase the coverage of each test cases, which give us better fitness


**********TO SEE FITNESS PROGREES******
uncomment line 102 of GeneticAlgorithm.java class to show the fiteness progress in the cvs file




############# output

The best  best individual is printed in the console. Also, the permuation of the best testsuite is printed in "solution.csv" file



