package com.mon.masterofnumber.engine;

public class LevelInfo
{
    public int MinValue;
    public int MaxValue;
    public Boolean EnableDecimal;
    public int Columns;
    public int Rows;
    public int Tot;
    public OrderTypes OrderType;
    public Boolean Shuffle;
    public int TotProximityNumber;
    public int Time;
    public int MalusTime;

    private void SetInfo(int columns, int rows, int time, int malus)
    {
        Columns = columns;
        Rows = rows;
        Time = time;
        MalusTime = malus;

    }

    public LevelInfo(int level,int gamesWithoutError)
    {
        Time = 30;
        MalusTime = 0;


        Columns = 2;
        Rows = 2;
        if (level >= 2 && level <= 3) SetInfo(3, 2,25,0);
        else if (level >= 4 && level <= 9) SetInfo(3, 3,30,2);
        else if (level >= 10 && level <= 15) SetInfo(3, 4,40,2);
        else if (level >= 16 && level <= 20) SetInfo(4, 4,50,3);
        else if (level >= 21 && level <= 25) SetInfo(4, 5,60,3);
        else if (level >= 26 && level <= 30) SetInfo(5, 5, 70, 4);
        else if (level >= 31 && level <= 35) SetInfo(3, 4, 80, 4);
        else if (level >= 36 && level <= 40) SetInfo(4, 4, 90, 4);
        else if (level >= 41 && level <= 45) SetInfo(4, 5, 100, 5);
        else if (level >= 46 && level <= 50) SetInfo(5, 5, 110, 5);
        else if (level >= 51 && level <= 55) SetInfo(3, 4, 120, 5);
        else if (level >= 56 && level <= 60) SetInfo(4, 4, 130, 5);
        else if (level >= 61 && level <= 65) SetInfo(4, 5, 140, 5);
        else if (level >= 66) SetInfo(5, 5,150,10);

        if (gamesWithoutError >= 3)
        {
            Time += 5;

        }

        OrderType = OrderTypes.Ascending;
        if (level == 3 ||
                (level >= 7 && level <= 9) ||
                (level >= 13 && level <= 15) ||
                (level >= 19 && level <= 20) ||
                (level >= 24 && level <= 25) ||
                (level >= 29 && level <= 30) ||
                (level >= 34 && level <= 35) ||
                (level >= 39 && level <= 40) ||
                (level >= 44 && level <= 45) ||
                (level >= 49 && level <= 50) ||
                (level >= 54 && level <= 55) ||
                (level >= 59 && level <= 60) ||
                (level >= 64 && level <= 65))
        {
            OrderType = OrderTypes.Descending;
        }

        Shuffle = false;
        if ((level == 6 || level == 9) ||
                (level == 12 || level == 15) ||
                (level == 18 || level == 20) ||
                (level == 23 || level == 25) ||
                (level == 28 || level == 30) ||
                (level == 33 || level == 35) ||
                (level == 38 || level == 40) ||
                (level == 43 || level == 45) ||
                (level == 48 || level == 50) ||
                (level == 53 || level == 55) ||
                (level == 58 || level == 60) ||
                (level == 63 || level == 65) || level > 65)
        {
            Shuffle = true;
        }

        Tot = Columns * Rows;

        EnableDecimal = false;
        MaxValue = 20 * (level + 1);
        if (level > 30) MinValue = (5 * (level + 1)) * -1;
        if (level > 50) EnableDecimal = true;
        if (EnableDecimal)
        {
            TotProximityNumber = 1;
            if (level > 60) TotProximityNumber = 2;
            if (level > 65) TotProximityNumber = 3;
        }
    }


}
