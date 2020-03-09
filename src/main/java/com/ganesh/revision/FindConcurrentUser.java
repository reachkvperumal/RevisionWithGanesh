package com.ganesh.revision;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class FindConcurrentUser{

    private static class Frequency implements Comparable<Frequency>{
        private int startTime;
        private int endTime;
        private int concurrentTime;

        public Frequency(int startTime, int endTime, int concurrentTime){
            this.startTime = startTime;
            this.endTime =  endTime;
            this.concurrentTime = concurrentTime;
        }

        @Override
        public int compareTo(Frequency o) {
            int result = 0;
            if(this.startTime < o.startTime){
                if(this.endTime > o.endTime || this.endTime < o.endTime){
                    result = -1;
                }
            }else if(this.startTime > o.startTime && this.endTime > o.endTime){
                result = 1;
            }
            return result;
        }


    }

    static int getMaxConcurrentAcrossAllTime(Frequency[] frequencies){
        Deque<Frequency> deque = new ArrayDeque<>();

        deque.offerFirst(frequencies[0]);

        int count = 1;
        for(int i =1; i<frequencies.length; i++){
            Frequency cur = frequencies[i];
            Frequency prev = deque.peek();

            if(cur.endTime < prev.endTime || cur.endTime > prev.endTime && cur.startTime < prev.endTime){
                count++;
                deque.removeFirst();
                deque.offerFirst(new Frequency(prev.startTime,cur.endTime,0));
            }
        }

        return count;
    }

    static int getNumberOfUsersAt(Frequency[] frequencies,int time){
        int count = 0;
        for(Frequency fr : frequencies){
            if(fr.startTime <= time || fr.endTime <= time)
                count++;
        }

        return count;
    }

    public static void main(String[] args) {
        int[][] arr = {{3,12},{1,10},{2,7},{3,12},{9,13},{11,14}};
        Frequency[] frequencies = Arrays.stream(arr).map(o -> new Frequency(o[0], o[1], 0)).sorted()
                .toArray(Frequency[]::new);
        System.out.println(getMaxConcurrentAcrossAllTime(frequencies));
        System.out.println(getNumberOfUsersAt(frequencies,3));
    }
}
