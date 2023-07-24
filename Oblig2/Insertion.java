class Insertion extends Sorter {

    void sort() {
        int i = 1;
        int j = 0;
        while(lt(i, n)){
            j = i;
            while(gt(j, 0) && gt(A[j-1],A[j])){
                swap(j-1, j);
                j--;
            }
            i++;
        }
        

    }

    String algorithmName() {
        return "insertion";
    }
}