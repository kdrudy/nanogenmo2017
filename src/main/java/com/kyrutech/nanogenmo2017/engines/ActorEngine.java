package com.kyrutech.nanogenmo2017.engines;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.person.PersonProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActorEngine {

    public Actor generateActor() {
        Fairy f = Fairy.create();
        Person p = f.person();

        Actor a = initActor(p);

        return a;
    }

    private Actor initActor(Person p) {
        Actor a = new Actor();
        a.setFirstName(p.getFirstName());
        a.setLastName(p.getLastName());
        a.setAge(p.getAge());
        if(p.getSex() == Person.Sex.FEMALE) {
            a.setSex(Actor.Sex.FEMALE);
        } else {
            a.setSex(Actor.Sex.MALE);
        }

        a.setMotivation(getRandomMotivation());
        a.setPositiveTraits(getRandomTraits(1));
        a.setNeutralTraits(getRandomTraits(0));
        a.setNegativeTraits(getRandomTraits(-1));
        return a;
    }

    public Actor generateActor(Actor a) {
        List<PersonProperties.PersonProperty> properties = new ArrayList<>();
        if(a.getFirstName()!=null) {
            properties.add(PersonProperties.withFirstName(a.getFirstName()));
        }
        if(a.getLastName()!=null) {
            properties.add(PersonProperties.withLastName(a.getLastName()));
        }
        if(a.getAge()!=null) {
            properties.add(PersonProperties.withAge(a.getAge()));
        }
        if(a.getSex()!=null) {
            switch(a.getSex()) {
                case FEMALE:
                    properties.add(PersonProperties.female());
                    break;
                case MALE:
                    properties.add(PersonProperties.male());
                    break;
            }
        }
        Fairy f = Fairy.create();
        Person p = f.person(properties.toArray(new PersonProperties.PersonProperty[properties.size()]));

        Actor newActor = initActor(p);
        if(a.getMotivation()!=null) {
            newActor.setMotivation(a.getMotivation());
        }

        return newActor;
    }

    private Actor.Motivation getRandomMotivation() {
        Actor.Motivation[] motives = Actor.Motivation.values();
        int rand = (int) (Math.random()*motives.length);
        return motives[rand];
    }

    private List<Trait> getRandomTraits(int type) {
        List<Trait> traits = new ArrayList<>();

        List<Trait> positive = Arrays.stream(Trait.values()).filter((t) -> t.getValue() == type).collect(Collectors.toList());

        int numOfTraits = (int) (Math.random()*2)+1;
        for(int i = 0;i<numOfTraits;i++) {
            int index = (int) (Math.random()*positive.size());
            traits.add(positive.remove(index));
        }

        return traits;
    }

    public static void main(String[] args) {
        ActorEngine ae = new ActorEngine();

        Actor a = ae.generateActor();

        System.out.println(a);
    }
}
