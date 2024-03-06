package com.mycompany.a3;

import java.util.List;

public interface IStrategy 
{
    void apply(NonPlayerRobot npr, Robot playerRobot, List<Bases> bases);
}

