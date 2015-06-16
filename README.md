# [Assembling the Tree of Life: Enterobacteriaceae] (http://atol.genetics.wisc.edu/)
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

![image of previous discovery] (https://github.com/asobin/ToLRepo/blob/master/images/orthologGroupPics.jpg)

These are the distributions of the OrthoMCL outputs for 312 taxa including our 110. A) Shows the distribution of the number of genomes represented in a given ortholog group. Most ortholog groups represent only a small number of genomes.  B) Same plot as (A) but zoomed in vertical axis to show spike in the [250,300] genomes range. C) Shows the distribution of the number of genes in a given ortholog group with respect to the number of ortholog groups in total. Most ortholog groups contain only a small number of genes. D) Is a zoomed in version of (C) meant to emphasize the spike between [250,300].

### Methods
![image of the methods] (https://github.com/asobin/ToLRepo/blob/master/images/methods.jpg)

### Results

![result pt 1](https://github.com/asobin/ToLRepo/blob/master/images/resultno1.jpg)

**Figure 1--Species tree of the family Enterobacteriaceae.** 
 (The leaf names on the tree are encoded)
=====


![result pt 2](https://github.com/asobin/ToLRepo/blob/master/images/result2.png)

**Figure 2--Tables describing the edge comparisons of the tree. Each table summarizes a specific set or subset of the tree where #’s refer to the number of proteins lost/added/shared at a given edge.** 
=====


![result pt 3](https://github.com/asobin/ToLRepo/blob/master/images/result3.jpg)

**Figure 3--Describes the effect that sharing and losing proteins has on the internal nodes length of the tree’s edges.**
=====


![result pt 4](https://github.com/asobin/ToLRepo/blob/master/images/result4.jpg)

**Figure 4--Describes the effect that sharing and losing proteins has on the tip's length of the tree’s edges.**
=====

## Conclusion
* **The file system developed here contains the specific protein variations for the entire Enterobacteriaceae Family.**
* **For the tree, it was determined that length of the branches is correlated to number of proteins shared between parent and child node.**

###### Supplementary Data
If you are interested in: 
* The file system that has the results
* A better resolution image of the species tree
* A file that decodes the names listed on the tree
* A summary table of the results

Please follow this link: https://uwmadison.box.com/s/y4afol8u015md2k8snjqe9zjmchgdetp
