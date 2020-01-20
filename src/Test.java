//FibonacciHeap Tester

import java.util.ArrayList;
import java.util.Collections;




public class Test {

    static FibonacciHeap heap;
    static FibonacciHeap fibonacciHeap;
    static double grade;
    static double testScore;

    public static void main(String[] args) {

        grade = 80.0;
        testScore = grade / 29;
        
        
        try {test0();} catch (Exception e){bugFound("test0");}
        try {test1();} catch (Exception e){bugFound("test1");}
        try {test2();} catch (Exception e){bugFound("test2");}
        try {test3();} catch (Exception e){bugFound("test3");}
        try {test4();} catch (Exception e){bugFound("test4");}
        try {test5();} catch (Exception e){bugFound("test5");}
        //try {test6();} catch (Exception e){bugFound("test6");} //REPEAT
        try {test7();} catch (Exception e){bugFound("test7"); System.out.println(e.getMessage()+e.getLocalizedMessage());} 
        try {test8();} catch (Exception e){bugFound("test8");}
        try {test9();} catch (Exception e){bugFound("test9");}
        try {test10();} catch (Exception e){bugFound("test10");}
        try {test11();} catch (Exception e){bugFound("test11");}
        try {test12();} catch (Exception e){bugFound("test12");}
        try {test13();} catch (Exception e){bugFound("test13");}
        try {test14();} catch (Exception e){bugFound("test14");}
        try {test15();} catch (Exception e){bugFound("test15");}
        try {test16();} catch (Exception e){bugFound("test16");}
        try {test17();} catch (Exception e){bugFound("test17");}
        try {test18();} catch (Exception e){bugFound("test18");}
        try {test19();} catch (Exception e){bugFound("test19");}
        try {test20();} catch (Exception e){bugFound("test20");}
        try {test21();} catch (Exception e){bugFound("test21");}
        try {test22();} catch (Exception e){bugFound("test22");}
        try {test23();} catch (Exception e){bugFound("test23");}
        try {test24();} catch (Exception e){bugFound("test24");}
        try {test25();} catch (Exception e){bugFound("test25");}
        try {test26();} catch (Exception e){bugFound("test26");}
        try {test27();} catch (Exception e){bugFound("test27");}
        try {test28();} catch (Exception e){bugFound("test28");}

        //test from whatsapp
        for (int r = 1; r < 20; r++) {
			fibonacciHeap = new FibonacciHeap();
			for (int i = -1; i < (int) Math.pow(2, r); i++) {
				fibonacciHeap.insert(i);
			}
			fibonacciHeap.deleteMin();

			int[] expected = new int[r];
			for (int i = 0; i < r; i++) {
				expected[i] = i;
			}
			int[] res = FibonacciHeap.kMin(fibonacciHeap, r);
			for (int i = 0; i < r; i++) {
				if (expected[i] != res[i]) {
					bugFound("test");
				}
			}
		}
        
        System.out.println(grade);
    }
    
    static void test0() {
        String test = "test0";
        fibonacciHeap = new FibonacciHeap();

        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < 99999; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        for (int i = 0; i < 99999; i++) {
            fibonacciHeap.insert(numbers.get(i));
        }

        for (int i = 0; i < 99999; i++) {
            if (fibonacciHeap.findMin().getKey() != i) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }
    }

    static void test1() {
        String test = "test1";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        while (!(heap).isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test2() {
        String test = "test2";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeysReverse(0);
        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test3() {
        String test = "test3";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        addKeysReverse(4000);
        addKeys(2000);
        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test4() {
        String test = "test4";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        addKeysReverse(4000);
        addKeys(2000);

        for (int i = 0; i < 1000; i++) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        addKeys(6000);
        addKeysReverse(8000);
        addKeys(10000);

        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test5() {
        String test = "test5";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        //addKeys(0);
        //addKeys(0);

        for (int i = 0; i < 1000; i++) {
            //for (int j = 0; j < 3; j++) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }
                fibonacciHeap.deleteMin();
           // }
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test6() {
        String test = "test6";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeysReverse(1000);
        addKeysReverse(1000);
        addKeys(0);
        addKeys(0);
        addKeys(1000);
        addKeys(1000);
        addKeysReverse(0);
        addKeysReverse(0);

        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }
                fibonacciHeap.deleteMin();
            }
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test7() {
        String test = "test7";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        addKeysReverse(3000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 2000; i < 2500; i++) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
            	test+=" loop 1";
            	System.out.println("hi1");
                bugFound(test);
                return;
            }
            heap.delete(nodes.get(i));
            fibonacciHeap.delete(nodes.get(i - 2000));
        }

        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
            	test+=" loop 2";
            	System.out.println("hi2");
            	bugFound(test);
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test+ " :not empty");
    }

    static void test8() {
        String test = "test8";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 2000; i < 2500; i++) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test+" loop 1");
                System.out.println("hi1");
                return;
            }
            heap.delete(nodes.get(i));
            fibonacciHeap.delete(nodes.get(i - 2000));
        }

        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test+=" loop 2");
                System.out.println("hi2");
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test9() {
        String test = "test9";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 2700; i > 2200; i--) {      	
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {       	
                bugFound(test+= "loop 1");
                System.out.println("hi1");
                return;
            }
            heap.delete(nodes.get(i));
            fibonacciHeap.delete(nodes.get(i - 2000));
        }

        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test+= " loop 2");
                System.out.println("hi2");
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test+ "not empty");
    }

    static void test10() {
        String test = "test10";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = 2000; i < 3000; i++) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }
        heap.deleteMin();
        fibonacciHeap.deleteMin();

        for (int i = 2700; i > 2200; i--) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
                bugFound(test+" loop 1");
                System.out.println("hi1");
                return;
            }
            heap.delete(nodes.get(i));
            fibonacciHeap.delete(nodes.get(i - 2000));
        }

        while (!heap.isEmpty()) {
            if (heap.findMin().getKey() != fibonacciHeap.findMin().getKey() || heap.size() != fibonacciHeap.size()) {
            	System.out.println("hi2");
                bugFound(test+" loop 2");
                return;
            }
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test+ "not empty");
    }

    static void test11() {
        String test = "test11";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9999);
        fibonacciHeap.decreaseKey(h, 9999);

        if (0 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
            return;
        }

        fibonacciHeap.deleteMin();

        for (int i = 1000; i < 2000; i++) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test12() {
        String test = "test12";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(5000);
        fibonacciHeap.decreaseKey(h, 4000);


        for (int i = 0; i < 2; i ++) {

            if (1000 != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }

        for (int i = 1001; i < 2000; i++) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test13() {
        String test = "test13";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9000);
        fibonacciHeap.decreaseKey(h, 4000);

        for (int i = 1000; i < 2000; i++) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }
        if (5000 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
            return;
        }
        fibonacciHeap.deleteMin();

        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test14() {
        String test = "test14";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        addKeysReverse(7000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9000);
        fibonacciHeap.decreaseKey(h, 4000);

        for (int i = 1000; i < 2000; i++) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }
        if (5000 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
            return;
        }
        fibonacciHeap.deleteMin();

        for (int i = 7000; i < 8000; i++) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }


    static void test15() {
        String test = "test15";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        for (int i = 1000; i < 10000; i += 1000) {
            addKeys(i);
        }

        fibonacciHeap.deleteMin();

        FibonacciHeap.HeapNode h = fibonacciHeap.insert(99999);
        fibonacciHeap.decreaseKey(h, 99999);

        if (0 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
            return;
        }

        fibonacciHeap.deleteMin();

        for (int i = 1001; i < 10000; i++) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }
        if (!fibonacciHeap.isEmpty())
            bugFound(test);
    }

    static void test16() {
        String test = "test16";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);

        if (fibonacciHeap.potential() != 3 ||
            FibonacciHeap.totalCuts() - cuts != 0 ||
            FibonacciHeap.totalLinks() - links != 0 ||
            fibonacciHeap.countersRep()[0] != 3)
            bugFound(test);
    }

    static void test17() {
        String test = "test17";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        if (fibonacciHeap.potential() != 1 ||
            FibonacciHeap.totalCuts() - cuts != 0 ||
            FibonacciHeap.totalLinks() - links != 1 ||
            fibonacciHeap.countersRep()[0] != 0 ||
            fibonacciHeap.countersRep()[1] != 1)
            bugFound(test);
    }

    static void test18() {
        String test = "test18";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();

        if (fibonacciHeap.potential() != 1 ||
            FibonacciHeap.totalCuts() - cuts != 0 ||
            FibonacciHeap.totalLinks() - links != 3 ||
            fibonacciHeap.countersRep()[0] != 0 ||
            fibonacciHeap.countersRep()[1] != 0 ||
            fibonacciHeap.countersRep()[2] != 1)
            bugFound(test);
    }

    static void test19() {
        String test = "test19";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();

        fibonacciHeap.decreaseKey(node, 2);

        if (fibonacciHeap.potential() != 4 ||
                FibonacciHeap.totalCuts() - cuts != 1 ||
                FibonacciHeap.totalLinks() - links != 3)
            bugFound(test);
    }

    static void test20() {
        String test = "test20";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();



        fibonacciHeap.insert(4);
        FibonacciHeap.HeapNode node5 = fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node6 = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.decreaseKey(node6, 2);
        fibonacciHeap.decreaseKey(node5, 1);

        if (fibonacciHeap.potential() != 4 ||
                FibonacciHeap.totalCuts() - cuts != 1 ||
                FibonacciHeap.totalLinks() - links != 0)
            bugFound(test);
    }

    static void test21() {
        String test = "test21";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int treeSize = 32768;
        int sizeToDelete = 1000;


        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = treeSize; i < treeSize * 2; i++) {
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            fibonacciHeap.insert(i);
        }

        for (int i = 0; i < sizeToDelete; i++) {
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() != 1)
            bugFound(test);
    }

    static void test22() {
        String test = "test22";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int treeSize = 32768;
        int sizeToDelete = 1000;

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();

        for (int i = treeSize; i < treeSize * 2; i++) {
            nodes.add(fibonacciHeap.insert(i));
        }

        for (int i = 0; i < sizeToDelete; i++) {
            fibonacciHeap.insert(i);
        }

        for (int i = 0; i < sizeToDelete; i++) {
            fibonacciHeap.deleteMin();
        }


        if (fibonacciHeap.potential() != 1)
            bugFound(test);

        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        boolean noCascading = true;
        int iterationCuts;

        Collections.shuffle(nodes);

        for (int i = 0; i < treeSize; i ++) {
            iterationCuts = FibonacciHeap.totalCuts();

            fibonacciHeap.decreaseKey(nodes.get(i), nodes.get(i).getKey() - (treeSize - i));

            if (FibonacciHeap.totalCuts() - iterationCuts > 1)
                noCascading = false;
        }

        if (fibonacciHeap.potential() != treeSize ||
            FibonacciHeap.totalCuts() - totalCuts != treeSize - 1 ||
            FibonacciHeap.totalLinks() - links != 0 ||
            fibonacciHeap.countersRep()[0] != treeSize ||
            noCascading)
            bugFound(test);
    }

    static void test23() {
        String test = "test23";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 1000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size ||
            FibonacciHeap.totalCuts() - totalCuts != 0 ||
            FibonacciHeap.totalLinks() - links != 0)
            bugFound(test);
    }

    static void test24() {
        String test = "test24";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 2000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size ||
                FibonacciHeap.totalCuts() - totalCuts != 0 ||
                FibonacciHeap.totalLinks() - links != 0)
            bugFound(test);
    }

    static void test25() {
        String test = "test25";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 3000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size ||
                FibonacciHeap.totalCuts() - totalCuts != 0 ||
                FibonacciHeap.totalLinks() - links != 0)
            bugFound(test);
    }

    static void test26() {
        String test = "test26";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 1000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        for (int i = 0; i < size / 2; i++) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 ||
            FibonacciHeap.totalCuts() - totalCuts != 0 ||
            FibonacciHeap.totalLinks() - links  < size - 100)
            bugFound(test);
    }

    static void test27() {
        String test = "test27";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 2000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        for (int i = 0; i < size / 2; i++) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 ||
                FibonacciHeap.totalCuts() - totalCuts != 0 ||
                FibonacciHeap.totalLinks() - links  < size - 100)
            bugFound(test);
    }

    static void test28() {
        String test = "test28";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int size = 3000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for (int i = size; i > 0; i--) {
            fibonacciHeap.insert(i);
        }

        for (int i = 0; i < size / 2; i++) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 ||
                FibonacciHeap.totalCuts() - totalCuts != 0 ||
                FibonacciHeap.totalLinks() - links  < size - 100)
            bugFound(test);
    }

    static void bugFound (String test) {
        System.out.println("Bug found in " + test);
        grade -= testScore;
    }

   static void addKeys(int start) {
        for (int i = 0; i < 1000; i++) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }
    }

    static void addKeysReverse(int start) {
        for (int i = 999; i >= 0; i--) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }
    }
}

