package com.ldgmms.game;

public class Hero extends GenericUnit{
    // experience stats
    float exp;
    float[] jobExp;
    int[] jobLevels;
    int level;
    float[] levelRequirements;      // where exp needed to level up is stored
    float[] jobLevelRequirements;
    // base character stats, not associated with a specific job
    // equipment will have just 2 slots, 0 indicates primary class, 1 indicates secondary class
    int[] equipment;
    Equipment equipmentStorage;
    Skills baseSkills;
    Skills specialCombo;
    // to avoid confusion, I will be referencing the characters in-game "class" as their job
    // not sure if this will always remain represented as an int
    // each job int will also (most likely) be an index for the skills and jobExp arrays
    // this value will change quite a bit as the
    int job;
    int secondary;
    HeroClass assassin;
    HeroClass scholar;
    HeroClass ranger;
    HeroClass sorcerer;
    HeroClass barbarian;
    HeroClass currentJob;
    HeroClass secondaryJob;
    // job stats, each of these corresponds to a different class, levelling up class = higher value
    // each stat will act as a multiplier to increase success rate or magnitude of a skill
    int sneak;          // assassin, 0
    int intelligence;   // scholar, 1
    int accuracy;       // ranger, 2
    int wisdom;         // sorcerer, 3
    int brawn;          // barbarian, 4
    // each hero has skills from the Skills class stored in a hashtable in Skills class, not yet included
    // Skills[] skills;


    public Hero(int x, int y, int ID) {

        this.xPosition = x;
        this.yPosition = y;

        this.ID = ID;

        this.currentJob = null;
        this.job = -1;
        this.secondary = -1;
        this.secondaryJob = null;

        this.equipment = new int[2];

        this.equipmentStorage = new Equipment();

        this.poisonResistance = 5;
        this.cutResistance = 5;
        this.pierceResistance = 5;
        this.fireResistance = 5;
        this.iceResistance = 5;

        this.level = 1;
        this.exp = 0.0f;
        this.jobExp = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.levelRequirements = new float[]{100.0f, 250.0f, 625.0f, 1562.5f, 3906.25f, 9765.63f, 24414.1f, 61035.16f, 152587.9f, 381469.7f};
        this.jobLevelRequirements = new float[]{25.0f, 62.5f, 156.25f, 390.6f, 976.5f, 2441.25f, 6103.1f, 15257.8f, 38144.5f, 95361.3f};

        this.jobLevels = new int[]{1, 1, 1, 1, 1};

        this.strength = 5;
        this.defense = 5;
        this.magic = 5;

        // these are status stats that show how much health a hero has
        // heroes spend stamina and mana to use skills
        this.health = 100.0f;
        this.mana = 100.0f;
        this.stamina = 100.0f;

        this.sneak = 5;
        this.intelligence = 5;
        this.accuracy = 5;
        this.wisdom = 5;
        this.brawn = 5;

        this.specialCombo = new Skills();

        this.baseSkills = new Skills();
        this.baseSkills.addSkill(1, "Base Melee Attack", this.strength, 0, 0, 1);
        this.baseSkills.addSkill(2, "Base Magic Attack", this.magic, 0, 0, 1);
        this.baseSkills.addSkill(3, "Base Ranged Attack", this.strength/2, 0, 0, 2);

        // each hero's class starts with one skill each, which does damage equal to their class's stat
        this.assassin = new HeroClass(0, "Assassin");
        this.assassin.skills.addSkill(this.assassin.currentSkill, "Sneak Attack", this.sneak, 0, 5, 1);
        this.assassin.secondarySkill.addSkill(1, "Shadow Blitz", this.sneak, 0, 10, 1);
        this.assassin.currentSkill++;

        this.scholar = new HeroClass(1, "Scholar");
        this.scholar.skills.addSkill(this.scholar.currentSkill, "Mana Surge", this.intelligence, 5, 0, 1);
        this.scholar.secondarySkill.addSkill(1, "Studious Blast", this.intelligence, 10, 0, 1);
        this.scholar.currentSkill++;

        this.ranger = new HeroClass(2, "Ranger");
        this.ranger.skills.addSkill(this.ranger.currentSkill, "True Shot", this.accuracy, 0, 5, 2);
        this.ranger.secondarySkill.addSkill(1, "Throwing Knife", this.accuracy, 0, 10, 2);
        this.ranger.currentSkill++;

        this.sorcerer = new HeroClass(3, "Sorcerer");
        this.sorcerer.skills.addSkill(this.sorcerer.currentSkill, "Magic Strike", this.wisdom, 5, 0, 1);
        this.sorcerer.secondarySkill.addSkill(1, "Magical Spell", this.wisdom, 10, 0, 1);
        this.sorcerer.currentSkill++;

        this.barbarian = new HeroClass(4, "Barbarian");
        this.barbarian.skills.addSkill(this.barbarian.currentSkill, "Brute Force", this.brawn, 0, 5, 1);
        this.barbarian.secondarySkill.addSkill(1, "Unrelenting Hit", this.brawn, 0, 10, 1);
        this.barbarian.currentSkill++;

    }

    public boolean checkMaxLevel(){
        if (this.level == 10) {
            return true;
        }
        return false;
    }

    public int getID() { return this.ID; }


    private void setNewJob(int order, int jobID) {

        // change characters job or secondary value
        // order must be 0 or 1, indicating whether this is primary or secondary class

        if (order > 2) {
            // something wrong, don't do anything yet
            return;
        }

        if (order == 0) {
            this.job = jobID;

            if (this.job == 0) {
                this.strength = this.strength * this.jobLevels[0];
                this.health = this.health * this.jobLevels[0];
                this.currentJob = this.assassin;
            } else if (this.job == 1) {
                this.magic = this.magic * this.jobLevels[1];
                this.mana = this.mana * this.jobLevels[1];
                this.currentJob = this.scholar;
            } else if (this.job == 2) {
                this.strength = this.strength * this.jobLevels[2];
                this.health = this.health * this.jobLevels[2];
                this.currentJob = this.ranger;
            } else if (this.job == 3) {
                this.magic = this.magic * this.jobLevels[3];
                this.mana = this.mana * this.jobLevels[3];
                this.currentJob = this.sorcerer;
            } else if (this.job == 4) {
                this.strength = this.strength * this.jobLevels[4];
                this.health = this.health * this.jobLevels[4];
                this.currentJob = this.barbarian;
            }
        } else {
            // secondary class
            this.secondary = jobID;
            if(this.secondary == 0){
                this.strength += this.jobLevels[0];
                this.secondaryJob = this.assassin;
            }
            else if(this.secondary == 1){
                this.magic += this.jobLevels[1];
                this.secondaryJob = this.scholar;
            }
            else if(this.secondary == 2){
                this.strength += this.jobLevels[2];
                this.secondaryJob = this.ranger;
            }
            else if(this.secondary == 3){
                this.magic += this.jobLevels[3];
                this.secondaryJob = this.sorcerer;
            }
            else if(this.secondary == 4){
                this.strength += this.jobLevels[4];
                this.secondaryJob = this.barbarian;
            }

        }

        this.checkSpecialCombo();


    }

    private void checkSpecialCombo(){
        this.specialCombo.map.remove(1);
        if(this.job == 0 && this.secondary == 2){
            this.specialCombo.addSkill(1, "Shadow Shot", this.sneak*this.accuracy, 10, 15, 2);
        }
        else if(this.job == 1 && this.secondary == 2){
            this.specialCombo.addSkill(1, "Weakspot Shot", this.intelligence*this.accuracy, 5, 20, 2);
        }
        else if(this.job == 2 && this.secondary == 4){
            this.specialCombo.addSkill(1, "Bow Thrash", this.accuracy*this.brawn, 0, 25, 1);
        }
        else if(this.job == 3 && this.secondary == 4){
            this.specialCombo.addSkill(1, "Magical Thrash", this.wisdom*this.brawn, 15, 10, 1);
        }
        else if(this.job == 4 && this.secondary == 1){
            this.specialCombo.addSkill(1, "Calculated Charge", this.brawn*this.intelligence, 5, 20, 1);
        }
    }

    /* slot designates index of item being changed, must be 0 or 1
     equipment might be its own class at some point?
     for now, 10s digit will indicate the job associated with the equipment,
     1s digit will show attribute upgrade,
     e.g. 28 would mean job 2, with an 8 point increase to attribute associated with that class */
    public void changeEquipment(int slot, EquipmentData equipment) {

        int prevEquip = this.equipment[slot];
        int prevJob = prevEquip / 10;
        int prevGain = prevEquip % 10;
        int jobID = equipment.getJob();
        int attributeGain = equipment.getStatBoost();

        if (prevJob == 0) {
            this.sneak -= prevGain;
        } else if (prevJob == 1) {
            this.intelligence -= prevGain;
        } else if (prevJob == 2) {
            this.accuracy -= prevGain;
        } else if (prevJob == 3) {
            this.wisdom -= prevGain;
        } else if (prevJob == 4) {
            this.brawn -= prevGain;
        }


        if (jobID == 0) {
            this.sneak += attributeGain;
        } else if (jobID == 1) {
            this.intelligence += attributeGain;
        } else if (jobID == 2) {
            this.accuracy += attributeGain;
        } else if (jobID == 3) {
            this.wisdom += attributeGain;
        } else if (jobID == 4) {
            this.brawn += attributeGain;
        }

        setNewJob(slot, jobID);
    }

    public void gainExp(float exp) {

        if(this.job != -1){
            if (this.jobLevels[this.job] < 10) {
                // job level is also maxed out, don't do anything
                this.jobExp[this.job] += exp;
                if (this.jobExp[this.job] >= this.jobLevelRequirements[this.jobLevels[this.job] - 1]) {
                    this.levelUp(this.job);
                }
            }
        }

        if(this.level < 10){
            this.exp += exp;
            if (this.exp >= this.levelRequirements[this.level - 1]) {
                this.levelUp(-1);
            }
        }

        return;
    }

    private void levelUp(int job) {

        if (job == -1) {

            // this is for a base character level up
            // increment level and heroes stats

            this.level++;

            this.mana += 10;
            this.health += 10;
            this.stamina += 10;

            this.strength += 2;
            this.magic += 2;
            this.defense += 2;

            // this is to reset the actual value of their stats with multipliers

        } else {
            // this is for job level up
            // the specific job's level is incremented
            // the job's associated skill is also incremented
            // setNewJob() is called so that the multipliers increase

            this.jobLevels[this.job] += 1;

            if (job == 0) {
                this.sneak += 2;

                // new skills are unlocked only on even levels
                if (this.jobLevels[this.job] % 2 == 0) {
                    if (this.jobLevels[this.job] == 2) {

                        // this has potential to steal an item
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Pickpocket", this.sneak / 2, 0, 10, 1);
                        this.currentJob.currentSkill++;

                    } else if (this.jobLevels[this.job] == 4) {

                        // has potential to poison the target
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Poison Blade", this.sneak, 10, 0, 1);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 6) {

                        // inflicts piercing damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Shadow Strike", this.sneak + this.strength, 0, 15, 1);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 8) {

                        // heals the user by a portion of the damage dealt
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Leech Blade", this.sneak, 0, 20, 1);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 10) {
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Nightblood", this.sneak * this.strength, 0, 25, 1);
                        this.currentJob.currentSkill++;
                    }
                } else {
                    this.sneak += 1;
                }

            } else if (job == 1) {
                this.intelligence += 2;

                if (this.jobLevels[this.job] % 2 == 0) {
                    if (this.jobLevels[this.job] == 2) {

                        // has potential to do ice damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Ice Rune", this.intelligence, 10, 0, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 4) {

                        // has potential to do fire damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Fire Rune", this.intelligence, 15, 0, 1);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 6) {

                        //this requires a lot of stamina
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Hurl Book", this.intelligence + this.strength, 0, 75, 2);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 8) {

                        // has potential to inflict random status effect on target
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Elemental Surge", this.intelligence + this.magic, 20, 0, 1);

                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 10) {

                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Mana Storm", this.intelligence * this.magic, 25, 0, 2);
                        this.currentJob.currentSkill++;
                    }
                } else {
                    this.intelligence += 1;
                }
            } else if (job == 2) {
                this.accuracy += 2;

                if (this.jobLevels[this.job] % 2 == 0) {
                    if (this.jobLevels[this.job] == 2) {

                        // inflicts piercing damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Piercing Arrow", this.accuracy, 0, 10, 2);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 4) {

                        // potential to inflict poison
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Poison Arrow", this.accuracy, 0, 15, 2);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 6) {

                        // has potential to inflict fire damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Fire Arrow", this.accuracy, 20, 0, 2);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 8) {

                        // pierce damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Double Shot", this.accuracy + this.strength, 0, 20, 3);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 10) {

                        // pierce damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Triple Shot", this.accuracy * this.strength, 0, 25, 3);
                        this.currentJob.currentSkill++;
                    }
                } else {
                    this.accuracy += 1;
                }
            } else if (job == 3) {
                this.wisdom += 2;

                if (this.jobLevels[this.job] % 2 == 0) {
                    if (this.jobLevels[this.job] == 2) {

                        // fire damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Fire Ball", this.wisdom, 10, 0, 2);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 4) {

                        // ice damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Blizzard", this.wisdom, 10, 0, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 6) {

                        // poison damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Poison", this.wisdom, 15, 0, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 8) {

                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Magic Blast", this.wisdom + this.magic, 10, 10, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 10) {

                        // chance to inflict any kind of damage type
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Elemental Storm", this.wisdom * this.magic, 25, 0, 2);
                        this.currentJob.currentSkill++;
                    }
                } else {
                    this.wisdom += 1;
                }
            } else if (job == 4) {
                this.brawn += 2;

                if (this.jobLevels[this.job] % 2 == 0) {
                    if (this.jobLevels[this.job] == 2) {

                        // cut damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Slicing Attack", this.brawn, 0, 10, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 4) {

                        // cut damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Blade Storm", this.brawn, 0, 10, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 6) {

                        // fire damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Raging Fire", this.brawn, 10, 5, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 8) {

                        // Ice Damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Frozen Strike", this.brawn + this.strength, 0, 20, 1);
                        this.currentJob.currentSkill++;
                    } else if (this.jobLevels[this.job] == 10) {

                        // cut damage
                        this.currentJob.skills.addSkill(this.currentJob.currentSkill,
                                "Storm of Swords", this.brawn, 0, 0, 1);
                        this.currentJob.currentSkill++;
                    }
                } else {
                    this.brawn += 1;
                }
            }

        }
        this.setNewJob(0, this.job);
        this.gainExp(0.0f);

    }

    public void pickUpEquipment(EquipmentData e){
        this.equipmentStorage.addEquipment(e.getKey(), e.getEquipmentName(), e.getJob(), e.getStatBoost());
        return;
    }

    public static void main(String[] args) {
        Hero hero = new Hero(0, 0, 1);
        hero.equipmentStorage.addEquipment(1, "sword", 4, 7);
        hero.changeEquipment(0, hero.equipmentStorage.map.get(1));
        hero.gainExp(5030.56f);
        System.out.println(hero.level);
        System.out.println(hero.jobLevels[hero.job]);
        hero.currentJob.skills.printMap();
    }
}