package com.oscuro.nasa.api.repositories;

import com.oscuro.nasa.api.entities.NasaApodEntity;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class NasaApodRepository {

    @PersistenceContext
    EntityManager em;

    public void save(NasaApodEntity nasaApodEntity) {
        em.persist(nasaApodEntity);
    }

    public NasaApodEntity findById(Long id) {
        return em.find(NasaApodEntity.class, id);
    }

    public LocalDate getLastApodDate() {
        List<LocalDate> result = em.createQuery("SELECT date FROM NasaApodEntity ORDER BY date DESC LIMIT 1", LocalDate.class).getResultList();
        return !result.isEmpty() ? result.get(0) : null;
    }

    public List<NasaApodEntity> findByDateRange(LocalDate from, LocalDate to, String sortBy, String sortOrder, Integer page, String searchString) {

        // 1. Base Native Query (Selecting all columns mapped to your entity)
        StringBuilder q = new StringBuilder("SELECT * FROM public.nasa_apod p WHERE 1=1");

        if (Objects.nonNull(from) && Objects.nonNull(to)) {
            q.append(" AND p.date >= :from AND p.date <= :to");
        }

        if (Objects.nonNull(searchString) && !searchString.isBlank()) {
            // Postgres Full-Text Search syntax for prefix matching (e.g. 'mar:*')
            //q.append(" AND to_tsvector('english', p.title || ' ' || p.explanation) @@ to_tsquery('english', :search)");

            // plainto_tsquery handles spaces and converts "mars rover" into "mars & rover" automatically
            //q.append(" AND to_tsvector('english', REGEXP_REPLACE(COALESCE(p.title, '') || ' ' || COALESCE(p.explanation, ''), '\\s+', ' ', 'g')) @@ plainto_tsquery('english', :search)");

            // 1. Convert COALESCE text to a string
            // 2. REGEXP_REPLACE looking for both normal whitespace (\s) AND explicitly hex code non-breaking spaces (\x00a0)
            //q.append(" AND to_tsvector('english', REGEXP_REPLACE(COALESCE(p.title, '') || ' ' || COALESCE(p.explanation, ''), '[\\s\\x00a0]+', ' ', 'g')) @@ plainto_tsquery('english', :search)");

            // Using Postgres case-insensitive regex match (~*) to find the word safely
            // This avoids all the tsvector/tsquery mapping errors completely
            q.append(" AND (p.title ~* :search OR p.explanation ~* :search)");
        }

        // 2. SAFE Dynamic Sorting (Whitelisting)
        String cleanSortBy = whitelistSortBy(sortBy);
        String cleanSortOrder = whitelistSortOrder(sortOrder);
        q.append(" ORDER BY p.").append(cleanSortBy).append(" ").append(cleanSortOrder);

        // Create Native Query and map result directly back to your JPA Entity
        Query query = em.createNativeQuery(q.toString(), NasaApodEntity.class);

        // 3. Bind Parameters
        if (Objects.nonNull(from) && Objects.nonNull(to)) {
            query.setParameter("from", from);
            query.setParameter("to", to);
        }

        if (Objects.nonNull(searchString) && !searchString.isBlank()) {
            // Prepare the string for to_tsquery.
            // We trim, replace spaces with ':* & ' and add ':*' at the end to make it a prefix search.
            // Example: "mars rover" becomes "mars:* & rover:*"
            /*
            String formattedSearch = searchString.trim()
                    .replaceAll("\\s+", ":* & ") + ":*";
            query.setParameter("search", formattedSearch);
             */
            //query.setParameter("search", searchString.trim());

            // Enforce a strict word boundary search or simple containment pattern
            // This ensures searching for "tidal" matches "tidal" cleanly anywhere in the text
            query.setParameter("search", searchString.trim());
        }

        // 4. Pagination
        int pageNumber = (page != null && page > 0) ? page : 1;
        query.setFirstResult((pageNumber - 1) * 50);
        query.setMaxResults(50);

        return query.getResultList();
    }

    // Prevent SQL Injection via order direction
    private String whitelistSortOrder(String order) {
        if (order != null && order.equalsIgnoreCase("ASC")) {
            return "ASC";
        }
        return "DESC"; // Default fallback
    }

    // Prevent SQL Injection via column names
    private String whitelistSortBy(String column) {
        Set<String> allowedColumns = Set.of("id", "date", "title"); // Add valid snake_case columns here
        if (column != null && allowedColumns.contains(column.toLowerCase())) {
            return column.toLowerCase();
        }
        return "date"; // Default fallback
    }

}
