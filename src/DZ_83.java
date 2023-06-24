import java.util.concurrent.*;

class DZ_83 {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3,new Good());

        new MyThred(cb,"B-");
        new MyThred(cb,"C-");
        new MyThred(cb,"D-");

        CountDownLatch cd1 = new CountDownLatch(11);
        CountDownLatch cd2 = new CountDownLatch(11);
        CountDownLatch cd3 = new CountDownLatch(11);
        CountDownLatch cd4 = new CountDownLatch(11);
        CountDownLatch cd5 = new CountDownLatch(11);
        CountDownLatch cd6 = new CountDownLatch(11);
        CountDownLatch cd7 = new CountDownLatch(11);
        CountDownLatch cd8 = new CountDownLatch(11);
        CountDownLatch cd9 = new CountDownLatch(11);
        CountDownLatch cd10 = new CountDownLatch(11);
        ExecutorService ex = Executors.newFixedThreadPool(2);

        ex.execute(new MyThred2(cd1,"A1"));
        ex.execute(new MyThred2(cd2,"A2"));
        ex.execute(new MyThred2(cd3,"A3"));
        ex.execute(new MyThred2(cd4,"A4"));
        ex.execute(new MyThred2(cd5,"A5"));
        ex.execute(new MyThred2(cd6,"A6"));
        ex.execute(new MyThred2(cd7,"A7"));
        ex.execute(new MyThred2(cd8,"A8"));
        ex.execute(new MyThred2(cd9,"A9"));
        ex.execute(new MyThred2(cd10,"A10"));

        try {
            cd1.await();
            cd2.await();
            cd3.await();
            cd4.await();
            cd5.await();
            cd6.await();
            cd7.await();
            cd8.await();
            cd9.await();
            cd10.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Good 2!");
        }

        ex.shutdown();
    }}

class MyThred2 implements Runnable {
    String name;
    CountDownLatch cbd;


    MyThred2(CountDownLatch cb, String name1) {
        cbd = cb;
        name = name1;
        new Thread(this);
    }

    public void run() {
        for (int i = 0; i < 11; i++) {
            System.out.println(name+" "+i);
            cbd.countDown();
        }

    }
}

     class Good implements Runnable {
         public void run() {
             System.out.println("Good 1!");
         }
     }

    class MyThred implements Runnable {
        CyclicBarrier cb;
        String name;

        MyThred(CyclicBarrier cb, String name) {
            this.cb = cb;
            this.name = name;
            new Thread(this).start();
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(name);
            }
            try {
                cb.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

