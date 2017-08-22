/*
 *  copyright (2016) Loke KS
    Reinforcement learning (not search)
    Find the the optimal path to the final state (State 10) from all other states
    The path is coded in the R table, a -1 means no connection, 0 is a connection
    100 is the reward i.e. final state because it always tries to maximize its
    rewards.
 */
package ql;

import java.util.ArrayList;
import java.util.Random;

public class QL {
    /*int[][] R={ {-1,-1,-1,-1,0,-1},
                {-1,-1,-1,0,-1, 100},
                {-1,-1,-1,0, -1,-1},
                {-1,0,0, -1,0,-1},
                {0,-1,-1,0, -1,100},
                {-1,0,-1,-1,0,100} };*/
    //only square matrix
    int[][] R={ {-1, 0,-1, 0,-1,-1,-1,-1,-1,-1, -1}, //0
                {-1,-1, 0,-1, 0,-1,-1,-1,-1,-1, -1}, //1
                {-1, 0,-1,-1,-1, 0,-1,-1,-1,-1, -1}, //2
                { 0,-1,-1,-1, 0,-1, 0,-1,-1,-1, -1}, //3
                {-1, 0,-1, 0,-1, 0,-1,-1,-1,-1, -1}, //4
                {-1,-1, 0,-1, 0,-1,-1, 0,-1,-1, -1}, //5
                {-1,-1,-1, 0,-1,-1,-1,-1, 0,-1, -1}, //6
                {-1,-1,-1,-1,-1, 0,-1,-1,-1,-1, 0},//7
                {-1,-1,-1,-1,-1,-1, 0,-1,-1, 0,-1},//8
                {-1,-1,-1,-1,-1,-1,-1,-1, 0,-100, -1},//9
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 100} //10
               // 0  1  2  3  4  5  6  7  8  9  10
    };
    /*
    States (X is not passable)
    |----------------|
    | 2 | 5 | 7 | 10 |
    |----------------|
    | 1 | 4 | X | 9  |
    |----------------|
    | 0 | 3 | 6 | 8  |
    |----------------|
    Rewards
    |-----------------|
    | 0 | 0 | 0 | 100 |
    |-----------------|
    | 0 | 0 | X | -100|
    |-----------------|
    | 0 | 0 | 0 | 0   |
    |-----------------|
Solution: 0->1->2->5->7->10
Solution: 1->2->5->7->10
Solution: 2->5->7->10
Solution: 3->4->5->7->10
Solution: 4->5->7->10
Solution: 5->7->10
Solution: 6->3->4->5->7->10
Solution: 7->10
Solution: 8->6->3->4->5->7->10 (Avoids -100 rewards!)
Solution: 9->10
Solution: 10->10    
    */
    
    
    double[][] Q;
    double gamma=0.7;
    double alpha=0.2;
    int maxRun=30;
    int size=R.length;
    int goalState=10;
    public QL(){
        Q=new double[size][size];
        Random r=new Random();
        int episode=0;
        printR();
        int start=1;
        do{
            int next=-1;
            do{
                ArrayList<Integer> actions=new ArrayList<Integer>(size);
                for(int i=0;i<size;i++){
                    if(R[start][i]!=-1)
                        actions.add(i);
                }
                
                int select=r.nextInt(actions.size());
                next=actions.get(select);

                ArrayList<Integer> nextactions=new ArrayList<Integer>(size);
                for(int i=0;i<size;i++){
                    if(R[next][i]!=-1)
                        nextactions.add(i);
                }
                double maxQ=-99999990;
                for(Integer in: nextactions){
                    if(maxQ<=Q[next][in])
                        maxQ=Q[next][in];
                }
                //System.out.println(maxQ+"<< "+start+"->"+next);
                
                //Q[start][next]=(1-alpha)*Q[start][next]+alpha*(R[start][next]+gamma*(maxQ-Q[start][next]));
                Q[start][next]=R[start][next]+gamma*maxQ;
                start=next;
                printQ();
                
            }while(next!=goalState);
            start=r.nextInt(size);
            next=-1;
            episode++;
            //System.out.println("Episode "+episode);
        }while(episode<maxRun);
        scaleQ();
        printQ();
    }
    
    public void printR(){
        System.out.println("R Val");
        for(int i=0;i<R.length;i++){
            for(int j=0;j<R.length;j++){
                System.out.print(R[i][j]+" ");
            }
            System.out.println("");
        }
    }
    public void printQ(){
        System.out.println("Q Val");
        for(int i=0;i<Q.length;i++){
            for(int j=0;j<Q.length;j++){
                System.out.printf(" %.2f",Q[i][j]);
            }
            System.out.println("");
        }
    }    
    public void scaleQ(){
        double max=-999;
        for(int i=0;i<Q.length;i++){
            for(int j=0;j<Q.length;j++){
                if(Q[i][j]>max)
                    max=Q[i][j];
            }
        }
        for(int i=0;i<Q.length;i++){
            for(int j=0;j<Q.length;j++){
                 Q[i][j]=Q[i][j]/max;
            }
        }        
    }    
    
    public void findSolution(int start){
        double max=-1;
        int next=-1;
        System.out.print("Solution: ");
        do{
            System.out.print(start+"->");
            for(int k=0;k<R.length;k++){
              if(max<Q[start][k]){
                  //System.out.println(max+" M ");
                  max=Q[start][k];
                  next=k;
              }
            }     
            start=next;
            
        }while(start!=goalState);
        System.out.println(""+start);
        
        
    }
    public static void main(String[] args) {
        QL q=new QL();
        for(int i=0;i<q.size;i++){
            q.findSolution(i);
        }
    }
    
}
