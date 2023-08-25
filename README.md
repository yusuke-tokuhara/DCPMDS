# DCPMDS
This program implements the DCPMDS algorithm to compute control categories in directed probabilistic networks.
# Requirements
This program calls IBM ILOG CPLEX Optimization Studio internally. To download CPLEX, see here (https://www.ibm.com/products/ilog-cplex-optimization-studio).
# Usage
Install java envioroment (https://www.oracle.com/jp/java/technologies/downloads/).

Download all class files.

The format of input network data is as follows:

v1 v2 0.5

v1 v3 0.3

v2 v3 0.2

v3 v4 0.7

v3 v5 0.8

v4 v5 0.15

v5 v6 0.4

The first column denotes a node (source node) from which a directed link goes to the second column node (sink node). The last column indicates the failure probability of the link. Also, each element should be separated by one-byte space.

Go to the class file directory and enter the following command:

java Main

After that, the program asks to input the path of the file where the input data is stored. 

Example:

Input folder path> C\\User\\sample_test\\input

Next, you have to input the path where the result output file is stored.

Example:

Output folder path> C\\User\\sample_test\\result

Finally, the user is asked to input the theta parameter value:

Example:

Parameter (0 < θ < 1) > 0.5
