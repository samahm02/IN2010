public class HeapSort extends Sorter{

    void sort() {
        buildMaxHeap(A, n);
        int i = n-1;
        while(gt(i,0)){
            swap(0, i);
            BubbleDown(A, 0, i);
            i--;
        }
    }

    void buildMaxHeap(int[] A, int n){
        int i = n/2;
        while(geq(i, 0)){
            BubbleDown(A, i, n);
            i--;
        }
    }

    void BubbleDown(int[] A, int i, int n){
        int largest = i;
        int left = 2*i +1;
        int right = 2*i +2;

        if (lt(left, n) && lt(A[largest], A[left])){
            swaps++;
            int tmp = largest;
            largest = left;
            left = tmp;
        }

        if(lt(right, n) && lt(A[largest], A[right])){
            swaps++;
            int tmp = largest;
            largest = right;
            right = tmp;
        }
        if (!eq(i,largest)){
            swap(i, largest);
            BubbleDown(A, largest, n);
        }
    }
    String algorithmName() {
        return "heapSort";
    }
}
    