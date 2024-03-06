package com.mycompany.a2;

import java.util.List;

public interface IStrategy 
{
    void apply(NonPlayerRobot npr, Robot playerRobot, List<Bases> bases);
}

