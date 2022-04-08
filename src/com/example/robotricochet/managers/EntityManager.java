package com.example.robotricochet.managers;

import com.example.robotricochet.entities.Entity;
import com.example.robotricochet.utils.Vector2i;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityManager {

    private final LinkedHashMap<Integer, Entity> entities = new LinkedHashMap<>();

    public void add(Entity entity) {
        entities.put(entity.getId(), entity);
    }
    public void add(Entity... entities) {
        for (Entity entity : entities) {
            add(entity);
        }
    }

    public Entity get(int id) {
        return entities.get(id);
    }

    public Set<Map.Entry<Integer, Entity>> getAll() {
        return entities.entrySet();
    }

    public Collection<Entity> getAllEntities() {
        return entities.values();
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Optional<T> find(Class<T> clazz) {
        return (Optional<T>) entities.values().stream()
                .filter(entity -> entity.getClass().equals(clazz))
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> findMany(Class<T> clazz) {
        return entities.values().stream()
                .filter(entity -> entity.getClass().equals(clazz))
                .map(entity -> (T) entity)
                .collect(Collectors.toList());
    }

    public void remove(int id) {
        entities.remove(id);
    }

    /**
     * Returns a list of entities that are clicked on.
     * The order corresponds to the z-index starting from the highest level.
     */
    public List<Entity> getEntitiesAtScreenCoords(Vector2i position) {
        List<Entity> targetEntities = entities.values().stream().filter(entity -> entity.getBounds().contains(position)).collect(Collectors.toList());
        Collections.reverse(targetEntities);
        return targetEntities;
    }

}
