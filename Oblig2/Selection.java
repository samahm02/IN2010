class Selection extends Sorter {

    void sort() {
        int i = 0;
        int k = 0;
        int j = 0;
        while(lt(i, n)){
            k = i;
            j=i+1;
            while(lt(j,n)){
                if(lt(A[j], A[k])){
                    k = j;
                }
                j++;
            }
            if (!eq(i,k)){
                swap(i, k);
            }
            i++;
        }
        
    }

    String algorithmName() {
        return "selection";
    }
}