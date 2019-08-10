package com.company;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Book {
    private String Name;
    private int Number = 3;
    private int Num;
    private Lock lock = new ReentrantLock();
    private Condition con = lock.newCondition();

    public void Set (String Name,int Num) {
        this.Name = Name;
        this.Num = Num;
    }
    public void Lent() throws InterruptedException{
        lock.lock();
        try {
            while(Number == 0) {
                System.out.println("没有书辣！！");
                con.await();
            }
            System.out.println("还剩" + Number--);
            con.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void put() throws InterruptedException{
       lock.lock();
       try {
           while(Number == 3){
               System.out.println("不用还书辣！！");
               con.await();
           }
           System.out.println("还剩" + Number++);
           con.signalAll();
       }finally {
           lock.unlock();
       }
    }
}
class Producer implements Runnable {
    private Book book;
    Producer(Book book){
        this.book = book;
    }
    public void run(){
        while(true){
            try {
                book.Lent();
            }catch (InterruptedException e){
                e.getStackTrace();
            }
        }
    }
}
class Consumer implements Runnable {
    private Book book;
    Consumer(Book book){
        this.book = book;
    }
    public void run(){
        while(true){
            try {
                book.put();
            }catch (InterruptedException e){
                e.getStackTrace();
            }
        }
    }
}
public class Main {

    public static void main(String[] args) {
        Book book = new Book();
        book.Set("语文",1);
        Producer pro = new Producer(book);
        Consumer cons = new Consumer(book);

        Thread t1 = new Thread(pro);
        Thread t2 = new Thread(pro);
        Thread t3 = new Thread(pro);
        Thread t4 = new Thread(pro);
        Thread t5 = new Thread(pro);
        Thread t6 = new Thread(cons);
        Thread t7 = new Thread(cons);
        Thread t8 = new Thread(cons);
        Thread t9 = new Thread(cons);
        Thread t10 = new Thread(cons);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();



    }
}
