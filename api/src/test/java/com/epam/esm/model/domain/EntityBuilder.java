package com.epam.esm.model.domain;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class EntityBuilder {
    private static final Random RANDOM = new Random();
    private static final List<String> BRANDS = extractFromUrl("https://raw.githubusercontent.com/Fat-Frumos/Cars/master/brands.txt");
    private static final List<String> TAGS = extractFromUrl("https://raw.githubusercontent.com/Fat-Frumos/Cars/master/tags.txt");
    private static final List<String> NAMES = extractFromUrl("https://raw.githubusercontent.com/Fat-Frumos/Cars/master/giftnames.txt");
    public static final List<String> USERNAMES = extractFromUrl("https://raw.githubusercontent.com/Fat-Frumos/Cars/master/usernames.txt");
    public static final List<String> DESC = extractFromUrl("https://raw.githubusercontent.com/Fat-Frumos/Cars/master/all.txt");
    private static final List<GiftCertificate> certificates = new ArrayList<>();

    public static String getRandomElement(List<String> list) {
        return list.remove(RANDOM.nextInt(list.size()));
    }

    public static List<String> extractFromUrl(String url) {
        try {
            return new BufferedReader(
                    new InputStreamReader(new URL(url)
                            .openStream()))
                    .lines().collect(toList());
        } catch (IOException e) {
            System.err.printf("%s file not found: %s%n", e.getMessage(), url);
        }
        return new ArrayList<>();
    }

    public static String generateDesc() {
        String brand = getRandomElement(BRANDS);
        String tag = getRandomElement(TAGS);
        String name = getRandomElement(NAMES);
        String desc = getRandomElement(DESC);
        return String.format("Get a %s %s %s for %s", name, brand, tag, desc);
    }

    public static List<GiftCertificate> generateCertificates(final int size) {
        IntStream.range(0, size)
                .mapToObj(i -> GiftCertificate.builder()
                        .name(getRandomElement(NAMES))
                        .description(generateDesc())
                        .tags(setTags()).build())
                .forEach(certificates::add);
        return certificates;
    }

    private static Set<Tag> setTags() {
        return IntStream.range(0, RANDOM.nextInt(3) + 1)
                .mapToObj(j -> Tag.builder().name(TAGS.get(j)).build())
                .collect(toSet());
    }

    public static List<User> generateUsers(final int size) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String randomName = getRandomElement(USERNAMES);
            users.add(User.builder().username(randomName).email(randomName + "@i.ua").build());
        }
        return users;
    }
}
//        System.err.println(USERNAMES.size() + " names");
