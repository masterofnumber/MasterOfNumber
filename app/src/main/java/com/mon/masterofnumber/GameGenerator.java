package com.games.quicklycount;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameGenerator {

    private Random _randomManager;
    private List<ToSort> _tmpSortList;
    private List<ToSort> _sortList;
    private List<String> _numbers;

    private List<Integer> _posUsed;

    private String _sequence1;
    private String _sequence2;
    private String _sequence3;
    private String _orderSequence1;
    private String _orderSequence2;
    private String _orderSequence3;


    public GameGenerator() {
        // TODO Auto-generated constructor stub
        _randomManager = new Random();
    }

    public void CreateGame(int level)
    {
        LevelInfo levelInf=new LevelInfo(level);
        CreateRound(levelInf,1);
        CreateRound(levelInf,2);
        CreateRound(levelInf,3);
    }

    public String GetSequence1()
    {
        return _sequence1;
    }

    public String GetSequence2()
    {
        return _sequence2;
    }

    public String GetSequence3()
    {
        return _sequence3;
    }

    public String GetOrderSequence1()
    {
        return _orderSequence1;
    }

    public String GetOrderSequence2()
    {
        return _orderSequence2;
    }

    public String GetOrderSequence3()
    {
        return _orderSequence3;
    }

    private void RandomPos(int count)
    {
        _posUsed = new ArrayList<Integer>();
        int index = 0;
        for (int i =0;i<count;i++)
        {
            boolean first = true;
            do
            {
                if (first)
                {
                    index=_randomManager.nextInt(count-1);
                }
                else
                {
                    for (int k = 0;k<count;k++)
                    {
                        if (!_posUsed.contains(k))
                        {
                            index=k;
                            break;
                        }
                    }
                }
                first=false;
            }while(_posUsed.contains(index));

            _posUsed.add(index);

            _sortList.add(_tmpSortList.get(index));
            _numbers.add(_tmpSortList.get(index).GetId());
        }
    }

    private  void CreateRound(LevelInfo level, int numberRound)
    {
        _tmpSortList = new ArrayList<ToSort>();
        _sortList = new ArrayList<ToSort>();
        _numbers = new ArrayList<String>();
        int count=12;
        if (numberRound==3) count=16;
        float number =0;
        float decPart=0;
        ToSort objNumber =null;
        boolean flReplace=false;
        int replaced=numberRound;

        for (int i=0;i<count;i++)
        {
            do
            {
                if (!flReplace)
                {

                    number=_randomManager.nextInt(level.MinValue()+level.MaxValue());
                    number=number+level.MinValue();
                }
                else
                {
                    number=Math.round(number);
                }
                if (level.EnableDecimal() )
                {
                    decPart=_randomManager.nextFloat();
                    if (decPart>0)
                    {
                        decPart=Math.round(decPart*100);
                        decPart=decPart/100;
                    }
                }
                number=number+decPart;
                if (level.EnableDecimal())
                    objNumber = new ToSort(number,Float.toString(number));
                else
                    objNumber = new ToSort(number,Float.toString(number).replace(".0", ""));

            }while(ExistValue(objNumber.GetValue()));

            _tmpSortList.add(objNumber);


            flReplace=false;
            if (level.EnableDecimal() && replaced>0)
            {
                replaced-=1;
                flReplace=true;
            }
        }
        RandomPos(count);
        Collections.sort(_sortList);

        String sequence ="";
        String orderSequence = "";
        for(ToSort toSort : _sortList){
            if (orderSequence=="")
                orderSequence=toSort.GetId();
            else
            {
                if (numberRound==2)
                    orderSequence=toSort.GetId()+";"+orderSequence;
                else
                    orderSequence+=";"+toSort.GetId();
            }
        }

        for(String value : _numbers){
            if (sequence=="")
                sequence=value;
            else
            {
                if (numberRound==2)
                    sequence=value+";"+sequence;
                else
                    sequence+=";"+value;
            }

        }

        switch (numberRound)
        {
            case 1:
                _sequence1=sequence;
                _orderSequence1=orderSequence;
                break;
            case 2:
                _sequence2=sequence;
                _orderSequence2=orderSequence;
                break;
            case 3:
                _sequence3=sequence;
                _orderSequence3=orderSequence;
                break;
        }
    }

    private boolean ExistValue(float value){
        for(ToSort toSort : _tmpSortList){
            if (toSort.GetValue()==value) return true;
        }
        return false;
    }

    public class LevelInfo
    {
        private int _minValue;
        private int _maxValue;
        private boolean _enableDecimal;

        public LevelInfo(int level)
        {
            _enableDecimal=false;
            _maxValue=20*(level+1);
            if (level>10) _minValue=(5*(level+1))*-1;
            if (level>20) _enableDecimal=true;
        }

        public int MinValue()
        {
            return _minValue;
        }

        public int MaxValue()
        {
            return _maxValue;
        }

        public boolean EnableDecimal()
        {
            return _enableDecimal;
        }
    }

    public class ToSort implements Comparable{

        private Float val;
        private String id;

        public ToSort(Float val, String id){
            this.val = val;
            this.id = id;
        }

        public Float GetValue()
        {
            return val;
        }

        public String GetId()
        {
            return id;
        }

        @Override
        public int compareTo(Object o) {

            ToSort f = (ToSort)o;

            if (val.floatValue() > f.val.floatValue()) {
                return 1;
            }
            else if (val.floatValue() <  f.val.floatValue()) {
                return -1;
            }
            else {
                return 0;
            }

        }

        @Override
        public String toString(){
            return this.id;
        }
    }

}



