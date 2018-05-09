package juc.sapmles;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 【栈限制】
 * 	loadTheArk方法的numParis，对于这些基本类型的本地变量，由于无法获取基本类型的引用，从语言语义确保了基本本地变量总是线程封闭的。
 * 
 * 这个例子主要是要说明这样1个问题：
 * 	loadTheArk() 没有将animals集合直接发布给外界，而是通过返回int基本类型，
 * 	从线程内部保护了animals集合中Animal对象的数据安全，防止了animals对象的逸出！
 * 
 * Thread confinement of local primitive and reference variables
 * 
 */
public class Test03ThreadRestrict {
	
    Ark ark;
    Species species;
    Gender gender;
    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals confined to method, don't let them escape!
        animals = new TreeSet<Animal>(new SpeciesGenderComparator()); // 按物种进行排序，然后在循环中进行配对
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a))
                candidate = a;
            else {
                ark.load(new AnimalPair(candidate, a));
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }


    class Animal {
        Species species;
        Gender gender;
        
        // 物种一致，性别不同，则为潜在配对
        public boolean isPotentialMate(Animal other) {
            return species == other.species && gender != other.gender;
        }
    }

    enum Species {
        AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA,
        IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS,
        PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH,
        UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
    }

    enum Gender {
        MALE, FEMALE
    }

    class AnimalPair {
        private final Animal one, two;

        public AnimalPair(Animal one, Animal two) {
            this.one = one;
            this.two = two;
        }
    }

    /**
     * 先按物种排序，再按性别排序
     */
    class SpeciesGenderComparator implements Comparator<Animal> {
        public int compare(Animal one, Animal two) {
            int speciesCompare = one.species.compareTo(two.species);
            return (speciesCompare != 0)
                    ? speciesCompare
                    : one.gender.compareTo(two.gender);
        }
    }

    class Ark {
        private final Set<AnimalPair> loadedAnimals = new HashSet<AnimalPair>();

        public void load(AnimalPair pair) {
            loadedAnimals.add(pair);
        }
    }
}

