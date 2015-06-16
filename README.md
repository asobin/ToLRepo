# Assembling the Tree of Life: Enterobacteriaceae
#### Determining and recording protein variations throughout the evolutionary history of the family Enterobacteriaceae
======
### Abstract
The aim of this study is to **determine the specific protein variations throughout the evolutionary history of the family Enterobacteriaceae based on genomic and predicted data.**  By writing a program that determines these variations it is now possible to analyze the rates, patterns, and identities of protein acquisitions and losses over the entire span of this family’s tree. This data should shed light on the genetic profiles of this highly studied family of bacteria and give other researchers clues as to which proteins are most vital, conserved, and or unique among this family’s 110 taxa.

### Introduction
* Previously, protein orthology data was determined for a group of 312 taxa (see Previous Discovery)
* Following this, a genome-scale species tree for 110 taxa that includes representatives of all named species of Enterobacteriaceae was built.
* We have conducted a large scale analysis of orthology among the proteins of these 110 taxa and created a character matrix describing the presence and absence of members of eac h ortholog group (set of orthologous genes) in each taxon.
* The species tree and orthology  data were input for a marginal ancestral state inference analysis. 
* The outputs were used in the PhyloTLaC program to determine protein variation across the species tree.

### Previous Discovery
![image of previous discovery] (url)
These are the distributions of the OrthoMCL outputs for 312 taxa including our 110. A) Shows the distribution of the number of genomes represented in a given ortholog group. Most ortholog groups represent only a small number of genomes.  B) Same plot as (A) but zoomed in vertical axis to show spike in the [250,300] genomes range. C) Shows the distribution of the number of genes in a given ortholog group with respect to the number of ortholog groups in total. Most ortholog groups contain only a small number of genes. D) Is a zoomed in version of (C) meant to emphasize the spike between [250,300].

### Methods
![image of the methods] (url)

### Results
![image of the results] (url)

## Conclusion
* **The file system developed here contains the specific protein variations for the entire Enterobacteriaceae Family.**
* **For the tree, it was determined that length of the branches is correlated to number of proteins shared between parent and child node.**
