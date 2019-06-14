package com.mon.masterofnumber.engine;

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

    public List<GameData> Rounds;

    public GameGenerator() {
        // TODO Auto-generated constructor stub
        _randomManager = new Random();
        Rounds = new ArrayList<>();
    }

    public void CreateGame(Integer level,ArcadeModes arcadeMode,Integer gamesWithoutError)
    {
        LevelInfo levelInf = new LevelInfo(level, gamesWithoutError);
        Integer count = 1;
        if (arcadeMode == ArcadeModes.Challenge)
            count = 3;
        for (Integer i=0;i<count;i++)
        {
            Rounds.add(CreateRound(levelInf, i+1));
        }
    }

    private void RandomPos(Integer count)
    {
        _posUsed = new ArrayList<Integer>();
        Integer index = 0;
        for (Integer i =0;i<count;i++)
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
                    for (Integer k = 0;k<count;k++)
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

    private GameData CreateRound(LevelInfo level,Integer levelWithoutErrors)
    {
        _tmpSortList = new ArrayList<>();
        _sortList = new ArrayList<>();
        _numbers = new ArrayList<>();
        int count = level.Tot;
        float   number = 0;
        float    decPart = 0;
        ToSort objNumber = null;
        boolean flReplace = false;
        int replaced = level.TotProximityNumber;

        for (Integer i = 0; i < count; i++)
        {
            do
            {
                if (!flReplace)
                {
                    number = _randomManager.nextInt(level.MinValue + level.MaxValue);
                    number = number + level.MinValue;
                }
                else
                {
                    number = Math.round(number);
                }
                if (level.EnableDecimal)
                {
                    decPart = _randomManager.nextFloat();
                    if (decPart > 0)
                    {
                        decPart = Math.round(decPart * 100);
                        decPart = decPart / 100;
                    }
                }
                number = number + decPart;
                if (level.EnableDecimal)
                    objNumber = new ToSort(number,Float.toString(number));
                else
                    objNumber = new ToSort(number, Float.toString(number).replace(".0", ""));

            } while (ExistValue(objNumber.GetValue()));

            _tmpSortList.add(objNumber);


            flReplace = false;
            if (level.EnableDecimal && replaced > 0)
            {
                replaced -= 1;
                flReplace = true;
            }
        }
        RandomPos(count);
        Collections.sort(_sortList);

        GameData game = new GameData();

        String currentIndex = "";

        if (level.OrderType == OrderTypes.Descending)
        {
            List<ToSort> reverseOrdList = new ArrayList<>();
            for (int i =0;i<_sortList.size();i++)
            {
                reverseOrdList.add(0, _sortList.get((i)));
            }
            _sortList = reverseOrdList;
        }

        for (String value: _numbers)
        {

            for (int ind=0;ind<_sortList.size();ind++)
            {
                if (_sortList.get(ind).id==value)
                {
                    currentIndex=Integer.toString( ind+1);
                    break;
                }
            }

            if (game.Sequance==null || game.Sequance == "")
            {
                game.Sequance = value;
                game.OrderNumberSequence= currentIndex;
            }
            else
            {
                game.Sequance += ";" + value;
                game.OrderNumberSequence += ";" + currentIndex;
            }
        }

        game.Rows = level.Rows;
        game.Columns = level.Columns;
        game.OrderType = level.OrderType;
        game.Shufle = level.Shuffle;
        game.Time = level.Time;
        game.MalusTime = level.MalusTime;

        return game;
    }

    private Boolean ExistValue(double value)
    {
        for (ToSort toSort:_tmpSortList)
        {
            if (toSort.GetValue() == value) return true;
        }
        return Boolean.FALSE;
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



