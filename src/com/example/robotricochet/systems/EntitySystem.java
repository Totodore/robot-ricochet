package com.example.robotricochet.systems;

import com.example.robotricochet.components.Vector2;
import com.example.robotricochet.entities.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySystem {
    private final LinkedHashMap<Integer, Entity> entities = new LinkedHashMap<>();

    public void add(Entity... entities) {
        for (Entity entity : entities)
            add(entity);
    }

    public void add(Entity entity) {
        entity.setEntitySystem(this);
        entities.put(entity.hashCode(), entity);
    }

    public void remove(Entity entity) {
        entities.remove(entity.hashCode());
    }

    public void remove(int id) {
        entities.remove(id);
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
    public <T> Optional<T> find(Class<T> clazz) {
        return (Optional<T>) entities.values().stream()
                .filter(entity -> clazz.isAssignableFrom(entity.getClass()))
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> findWhere(Class<T> clazz, Function<T, Boolean> predicate) {
        return (Optional<T>) entities.values().stream()
                .filter(entity -> clazz.isAssignableFrom(entity.getClass()) && predicate.apply((T) entity))
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findMany(Class<T> clazz) {
        return entities.values().stream()
                .filter(entity -> clazz.isAssignableFrom(entity.getClass()))
                .map(entity -> (T) entity)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findManyWhere(Class<T> clazz, Function<T, Boolean> predicate) {
        return entities.values().stream()
                .filter(entity -> clazz.isAssignableFrom(entity.getClass()) && predicate.apply((T) entity))
                .map(entity -> (T) entity)
                .collect(Collectors.toList());
    }

    public <T> void remove(Class<T> clazz) {
        find(clazz).ifPresent(entity -> remove((Entity) entity));
    }

    public <T> void removeWhere(Class<T> clazz, Function<T, Boolean> predicate) {
        findWhere(clazz, predicate).ifPresent(entity -> remove((Entity) entity));
    }

    @SuppressWarnings("unchecked")
    public <T> void removeMany(Class<T> clazz) {
        for(Entity e : (List<Entity>) findMany(clazz)) {
            remove(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void removeManyWhere(Class<T> clazz, Function<T, Boolean> predicate) {
        for (Entity e : (List<Entity>) findManyWhere(clazz, predicate)) {
            remove(e);
        }
    }

    public Stream<Entity> findUnderPoint(Vector2 position) {
        return entities.values().stream().filter(entity -> entity.getBounds().contains(position));
    }


    /**
     * Returns a list of entities that are clicked on.
     * The order corresponds to the z-index starting from the highest level.
     */
    public List<Entity> getEntitiesAtScreenCoords(Vector2 position) {
        List<Entity> targetEntities = entities.values().stream().filter(entity -> entity.getBounds().contains(position)).collect(Collectors.toList());
        Collections.reverse(targetEntities);
        return targetEntities;
    }
}
