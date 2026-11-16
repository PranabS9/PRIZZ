package models;

import java.util.HashSet;
import java.util.Set;

/**
 * [MODELS] Service Provider Entity.
 * Purpose: Represents a contractor/provider who bids on jobs.
 * Syllabus: Inheritance (extends User), Polymorphism, Collections (Set<String> for unique skills).
 */
public class ServiceProvider extends User {
    private Double rating;
    private Set<String> skillSet;

    public ServiceProvider(Integer userId, String name, String email, Double walletBalance, Double rating) {
        super(userId, name, email, "PROVIDER", walletBalance);
        this.rating = (rating != null) ? rating : 5.0;
        this.skillSet = new HashSet<>();
    }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Set<String> getSkillSet() { return skillSet; }

    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.skillSet.add(skill.trim());
        }
    }

    @Override
    public String getProfileSummary() {
        return String.format("Provider Profile: %s (ID: %d) | Rating: %.1f★ | Skills: %s",
                getName(), getUserId(), rating, skillSet);
    }
}

