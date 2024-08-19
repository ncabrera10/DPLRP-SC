Code: Checker and evaluator of solutions for the DPLRP

Author: Nicolas Cabrera

-------------------------------------------------------------------------------------------------------------
If you want to use this code:

1. Check that all the instances files from Cabrere et al. (2025) in txt format are on the "instances" folder.

2. Add all your solutions to the "solutions" folder. 

	2.1 Your solutions should be contained in a folder. For example: test
	
	2.2 Inside the folder, you should include the results files (txt) for each instance you want to test.
	
		2.2.1 The results file for a given instance should be called: Routes_EDFID_DynamismDegree_ArrivalRate_Distribution_NumberOfTasks_NumberOfWorkers
		
		2.2.2 For example: "Routes_1_0.05_0.006_CTD_50_6.txt"
		
		2.2.1 This file should have a line for each arc used in the solution with the notation: Tail;Head;TransportationMode;WorkerID
			TransportationMode: 1 -> driving 2 -> walking
		
3. Open the class "Main" in the "main" package.
4. Run as a Java Application.

All your results and reports will be store on the "results" folder.

----------------------------------------------------------------------------------------------------------

Parameters regarding the maximum walking distance, the maximum walking distance between two nodes and so on..
can be changed in the parametersGlobal.xml file in the config folder.

----------------------------------------------------------------------------------------------------------

This code is a work in progress. More constraints can be added in the "constraints package".
