# Semester Final Lab Exam Solution of SWE 20

### **Caution**: This has been solved in during the exam, so the answer might have some problem with them

## Lab exam details:

- [Lab question](./Distributed%20System%20Lab.pdf)
- [Dataset](./newspaper_dump_large.txt)

## Solution:

- **Problem 1**: Find the Longest Word(s) in a Text File
  - Runner/Driver Class: `LW_Runner.java`
  - Mapper Class: `LW_Mapper.java`
  - Reducer Class: `LW_Reducer.java`
  - Running command:
  ```
  hadoop jar target/ds-lab-1.0-SNAPSHOT.jar com.dslab.LW_Runner /input/newspaper_dump_large.txt /p1-output
  ```
- **Problem 2**: Word Count – Case Insensitive
  - Runner/Driver Class: `WC_Runner.java`
  - Mapper Class: `WC_Mapper.java`
  - Reducer Class: `WC_Reducer.java`
  - Running command:
  ```
  hadoop jar target/ds-lab-1.0-SNAPSHOT.jar com.dslab.WC_Runner /input/newspaper_dump_large.txt /p2-output
  ```
- **Problem 3**: Count Word Lengths
  - Runner/Driver Class: `WLC_Runner.java`
  - Mapper Class: `WLC_Mapper.java`
  - Reducer Class: `WLC_Reducer.java`
  - Running command:
  ```
  hadoop jar target/ds-lab-1.0-SNAPSHOT.jar com.dslab.WLC_Runner /input/newspaper_dump_large.txt /p3-output
  ```
