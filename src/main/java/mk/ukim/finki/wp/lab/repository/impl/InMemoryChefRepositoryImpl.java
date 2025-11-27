package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryChefRepositoryImpl implements ChefRepository {


    @Override
    public List<Chef> findAll() {
        return DataHolder.chefs;
    }

    @Override
    public Optional<Chef> findById(Long id) {
        return DataHolder.chefs
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Chef save(Chef chef) {
        if (chef.getId() != null)
            deleteById(chef.getId());
        else
            chef.setId((long) (Math.random() * 1000));

        DataHolder.chefs.add(chef);
        return chef;
    }

    @Override
    public void deleteById(Long id) {
        DataHolder.chefs.removeIf(chef -> chef.getId().equals(id));
    }
}
