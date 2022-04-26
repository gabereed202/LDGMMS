package com.ldgmms.game;

// class specific to jobs
public class HeroClass{

    // each hero has skills from the Skills class stored in a hashtable in Skills class, not yet included
    Skills skills;
    Skills secondarySkill;
    String jobName;
    int jobID;
    int currentSkill;


    public HeroClass(int jobID, String jobName){
        this.skills = new Skills();
        this.secondarySkill = new Skills();
        this.jobID = jobID;
        this.jobName = jobName;
        this.currentSkill = 1;

    }

}
