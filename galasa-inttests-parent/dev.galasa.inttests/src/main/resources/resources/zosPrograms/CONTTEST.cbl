       IDENTIFICATION DIVISION.
       PROGRAM-ID. CONTTEST.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 DATA-ITEMS.
       02 INPUT-DATA         PIC X(20).
       02 OUTPUT-DATA        PIC X(20).
       LINKAGE SECTION.
       PROCEDURE DIVISION.
           EXEC CICS GET CONTAINER('HOBBIT')
                FLENGTH(LENGTH OF INPUT-DATA)
                INTO(INPUT-DATA)
                END-EXEC.
           MOVE FUNCTION UPPER-CASE(INPUT-DATA) TO OUTPUT-DATA
           EXEC CICS PUT CONTAINER('HOBBIT')
                FROM(OUTPUT-DATA)
                END-EXEC.
           EXEC CICS RETURN END-EXEC.