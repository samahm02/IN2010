class Quick extends Sorter {

    void sort() {
        quickSort(A, 0, n-1);
    }

    int[] quickSort(int[] A, int low, int high){
        if (geq(low, high)){
            return A;
        }
        int p = partition(A, low, high);
        quickSort(A, low, p-1);
        quickSort(A, p+1, high);
        return A;
    }

    int partition(int[] A, int low, int high){
        int pivot = ChoosePivot(A, low, high);

        swap(pivot, high);
        pivot = A[high];
        int left = low;
        int right = high-1;

        while (leq(left, right)){
            while(leq(left, right) && leq(A[left], pivot)){
                left++;
            }
            while(geq(right, left) && geq(A[right], pivot)){
                right--;    
            }
            if (lt(left, right)){
                swap(left, right);
            }
        }
        swap(left, high);
        return left;

    }
    int ChoosePivot(int[] A, int low, int high){
        return middleOfThree(low, high, n/2);
    }

    public static int middleOfThree(int first, int second, int third) {
        if (second < first && first < third || third < first && first < second) {
          return first;
        }
    
        if (first < second && second < third || third < second && second < first) {
          return second;
        }
    
        return third;
      }

    String algorithmName() {
        return "quick";
    }
}