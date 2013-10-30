/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.seasons;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 10/29/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Season {
    SPRING("spring", 6),
    SUMMER("summer", 6),
    FALL("fall", 6),
    WINTER("winter", 6);

    private Season(String name, int lengthInDays)
    {
        if(lengthInDays < 0)
            lengthInDays = 0;

        DISPLAY_NAME = name;
        LENGTH_IN_DAYS = lengthInDays;
    }

    static
    {
        Season season[] = values();
        season[0].FIRST_DAY = 0;

        for(int i = 1; i < season.length; i++)
        {
            season[i].FIRST_DAY = season[i-1].FIRST_DAY + season[i-1].LENGTH_IN_DAYS;
        }
    }

    public int lengthInDays()
    {
        return LENGTH_IN_DAYS;
    }

    public String displayName()
    {
        return DISPLAY_NAME;
    }

    public Season next()
    {
        Season[] seasons = Season.values();
        return seasons[(this.ordinal() + 1) % seasons.length];
    }

    public Season previous()
    {
        Season[] seasons = Season.values();
        return seasons[(this.ordinal() + seasons.length - 1) % seasons.length];
    }

    public static Season onDay(double day)
    {
        return onDay((int) Math.floor(day));
    }

    public static Season onDay(int day)
    {
        day = ((day % CYCLE_LENGTH_IN_DAYS) + CYCLE_LENGTH_IN_DAYS) % CYCLE_LENGTH_IN_DAYS;

        for(Season season: values())
        {
            if(season.firstDay() <= day && day <= season.lastDay() )
                return season;
        }

        return null;  //unreachable code;
    }

    public static int dayOfCycle(double day)
    {
        return dayOfCycle((int)Math.floor(day));
    }

    public static int dayOfCycle(int day)
    {
        return ((day % CYCLE_LENGTH_IN_DAYS) + CYCLE_LENGTH_IN_DAYS) % CYCLE_LENGTH_IN_DAYS;
    }

    public static int dayOfSeason(double day)
    {
        return dayOfSeason((int)Math.floor(day));
    }

    public static int dayOfSeason(int day)
    {
        day = dayOfCycle(day);
        return day - onDay(day).firstDay();
    }

    public int firstDay()
    {
        return FIRST_DAY;
    }

    public int lastDay()
    {
       return FIRST_DAY + LENGTH_IN_DAYS - 1;
    }

    public final static int CYCLE_LENGTH_IN_DAYS;
    static
    {
        Season[] seasons = values();
        Season lastSeason = seasons[seasons.length - 1];
        CYCLE_LENGTH_IN_DAYS = lastSeason.lastDay() + 1;
    }

    private final String DISPLAY_NAME;
    private final int LENGTH_IN_DAYS;
    private int FIRST_DAY;
}