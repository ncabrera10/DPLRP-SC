#### 0. Libraries ####

  # This function verifies which packages are missing. If a package is missing, is automatically installed.
  
    foo <- function(x){
      for(i in x){
        #  Returns true if it was possible to load the package
        if(!require(i,character.only = TRUE)){
          #  If the package was not loaded, then we proceed to the installation
          install.packages(i,dependencies = TRUE)
          #  Load the package after the installation
          require( i , character.only = TRUE )
        }
      }
    }
  
  #  Load pakages:
  
    foo(c("readr"))

  # Select the path to the data:
  
    pathWhereSolutionsAreLocated = "/Users/nicolas.cabrera-malik/Documents/Work/Thesis/DPLRP/Code/EDF/DPLRP/results/"
    pathWhereSolutionsShouldBeLocated = "/Users/nicolas.cabrera-malik/Documents/Work/Thesis/DPLRP/Code/EDF/Checker/DPLRP-SC/solutions/"

#### 2. Functions ####
     
    relocate_files <- function(policy){
      
      # Create the destination folder for this policy:
      
      output_dir <- paste0(pathWhereSolutionsShouldBeLocated,policy,"/")
      if (!dir.exists(output_dir)) {dir.create(output_dir)}
      
      # Capture the names of the folders in the folder associated with the policy
      
        folder_names <- list.dirs(path = paste0(pathWhereSolutionsAreLocated,policy,"/"),recursive = FALSE)
        folder_names_abbr <- list.dirs(path = paste0(pathWhereSolutionsAreLocated,policy,"/"),full.names=FALSE,recursive = FALSE)
        
      # Iterate over each folder:
        
        for(i in 1:length(folder_names)){
          
          # Capture the current folder
          
          folder <- folder_names[i]
          folder_abbr <- folder_names_abbr[i]
          
          # Read the actual routes file:
          
          df <- read.table(file = paste0(folder,"/ActualRoutes.txt"),header=FALSE,sep=";")
          
          # Print the file in the correct folder:
          
          folder_abbr <- gsub("Instance","Routes",folder_abbr)
          write.table(df, file = paste0(pathWhereSolutionsShouldBeLocated,policy,"/",folder_abbr,".txt"), row.names = FALSE, col.names = FALSE,
                      sep = ";")
          
        }
        
        
        
    }
    
#### 2. USING THE CODE ####
    
  relocate_files("T-A-D-N")
  relocate_files("D-A-D-N") 
  