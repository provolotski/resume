package by.minsk.env;


import by.minsk.resume.model.LanguageLevel;
import by.minsk.resume.model.LanguageType;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.sql.*;
import java.util.*;

public class GenerateEnv {
    // logger
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateEnv.class);
    // JDBC settings
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/resumedb";
    public static final String JDBC_USERNAME = "resumeuser";
    public static final String JDBC_PASSWORD = "resumePassword";
    //constants for gen data
    public static final String JUNIOR_JAVA_DEVELOPER_POSITION = "Junior java developer position";
    public static final String TWO_YEAR_JAVA_DEVELOPER_POSITION = "Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)";
    //dictionaries

    private static final String[] COUNTRY = {"Беларусь", "Polsca"};
    private static final String[] CITIES = {"Витебск", "Минск", "Брест", "Гомель"};
    private static final String[] FOREGIN_LANGUAGES = {"Spanish", "French", "German", "Italian"};
    private static final String PASSWORD_HASH = "$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq";


    private static final String[] HOBBIES = {"Cycling", "Handball", "Football", "Basketball", "Bowling", "Boxing", "Volleyball", "Baseball", "Skating", "Skiing", "Table tennis", "Tennis",
            "Weightlifting", "Automobiles", "Book reading", "Cricket", "Photo", "Shopping", "Cooking", "Codding", "Animals", "Traveling", "Movie", "Painting", "Darts", "Fishing", "Kayak slalom",
            "Games of chance", "Ice hockey", "Roller skating", "Swimming", "Diving", "Golf", "Shooting", "Rowing", "Camping", "Archery", "Pubs", "Music", "Computer games", "Authorship", "Singing",
            "Foreign lang", "Billiards", "Skateboarding", "Collecting", "Badminton", "Disco"};
    public static final String LANGUAGES = "Languages";
    public static final String WEB_SERVERS = "Web Servers";
    public static final String BUILD_SYSTEM = "Build system";
    public static final String CLOUD = "Cloud";
    public static final String ECLIPSE_FOR_JEE_DEVELOPER = "Eclipse for JEE Developer";
    public static final String GITHUB = "Github";
    public static final String MAVEN = "Maven";
    public static final String DEV_STUDY_NET = "DevStudy.net";

    private static final List<LanguageType> languageTypes = new ArrayList<>(EnumSet.allOf(LanguageType.class));
    private static final List<LanguageLevel> languageLevels = new ArrayList<>(EnumSet.allOf(LanguageLevel.class));

    //paths
    private static final String MEDIA_DIR = "/home/kpss/Work/Java/resume/src/main/webapp/media";
    private static final String CERTIFICATES_PATH = "external/test-data/certificates";
    public static final String PHOTO_PATH = "external/test-data/photos";
    private static final String DUMMY_CONTENT_TEXT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. Sed cursus turpis vitae tortor. Donec posuere vulputate arcu. Phasellus accumsan cursus velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam, nisi quis porttitor congue, elit erat euismod orci, ac placerat dolor lectus quis orci. Phasellus consectetuer vestibulum elit. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc. Vestibulum fringilla pede sit amet augue. In turpis. Pellentesque posuere. Praesent turpis. Aenean posuere, tortor sed cursus feugiat, nunc augue blandit nunc, eu sollicitudin urna dolor sagittis lacus. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Nullam sagittis. Suspendisse pulvinar, augue ac venenatis condimentum, sem libero volutpat nibh, nec pellentesque velit pede quis nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Ut varius tincidunt libero. Phasellus dolor. Maecenas vestibulum mollis diam. Pellentesque ut neque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. In ac felis quis tortor malesuada pretium. Pellentesque auctor neque nec urna. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Aenean viverra rhoncus pede. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut non enim eleifend felis pretium feugiat. Vivamus quis mi. Phasellus a est. Phasellus magna. In hac habitasse platea dictumst. Curabitur at lacus ac velit ornare lobortis. Curabitur a felis in nunc fringilla tristique. Morbi mattis ullamcorper velit. Phasellus gravida semper nisi. Nullam vel sem. Pellentesque libero tortor, tincidunt et, tincidunt eget, semper nec, quam. Sed hendrerit. Morbi ac felis. Nunc egestas, augue at pellentesque laoreet, felis eros vehicula leo, at malesuada velit leo quis pede. Donec interdum, metus et hendrerit aliquet, dolor diam sagittis ligula, eget egestas libero turpis vel mi. Nunc nulla. Fusce risus nisl, viverra et, tempor et, pretium in, sapien. Donec venenatis vulputate lorem. Morbi nec metus. Phasellus blandit leo ut odio. Maecenas ullamcorper, dui et placerat feugiat, eros pede varius nisi, condimentum viverra felis nunc et lorem. Sed magna purus, fermentum eu, tincidunt eu, varius ut, felis. In auctor lobortis lacus. Quisque libero metus, condimentum nec, tempor a, commodo mollis, magna. Vestibulum ullamcorper mauris at ligula. Fusce fermentum. Nullam cursus lacinia erat. Praesent blandit laoreet nibh. Fusce convallis metus id felis luctus adipiscing. Pellentesque egestas, neque sit amet convallis pulvinar, justo nulla eleifend augue, ac auctor orci leo non est. Quisque id mi. Ut tincidunt tincidunt erat. Etiam feugiat lorem non metus. Vestibulum dapibus nunc ac augue. Curabitur vestibulum aliquam leo. Praesent egestas neque eu enim. In hac habitasse platea dictumst. Fusce a quam. Etiam ut purus mattis mauris sodales aliquam. Curabitur nisi. Quisque malesuada placerat nisl. Nam ipsum risus, rutrum vitae, vestibulum eu, molestie vel, lacus. Sed augue ipsum, egestas nec, vestibulum et, malesuada adipiscing, dui. Vestibulum facilisis, purus nec pulvinar iaculis, ligula mi congue nunc, vitae euismod ligula urna in dolor. Mauris sollicitudin fermentum libero. Praesent nonummy mi in odio. Nunc interdum lacus sit amet orci. Vestibulum rutrum, mi nec elementum vehicula, eros quam gravida nisl, id fringilla neque ante vel mi. Morbi mollis tellus ac sapien. Phasellus volutpat, metus eget egestas mollis, lacus lacus blandit dui, id egestas quam mauris ut lacus. Fusce vel dui. Sed in libero ut nibh placerat accumsan. Proin faucibus arcu quis ante. In consectetuer turpis ut velit. Nulla sit amet est. Praesent metus tellus, elementum eu, semper a, adipiscing nec, purus. Cras risus ipsum, faucibus ut, ullamcorper id, varius ac, leo. Suspendisse feugiat. Suspendisse enim turpis, dictum sed, iaculis a, condimentum nec, nisi. Praesent nec nisl a purus blandit viverra. Praesent ac massa at ligula laoreet iaculis. Nulla neque dolor, sagittis eget, iaculis quis, molestie non, velit. Mauris turpis nunc, blandit et, volutpat molestie, porta ut, ligula. Fusce pharetra convallis urna. Quisque ut nisi. Donec mi odio, faucibus at, scelerisque quis, convallis in, nisi. ";
    private static final List<String> SENTENCES;

    static {
        String[] sentences = DUMMY_CONTENT_TEXT.split("\\.");
        List<String> sentenceList = new ArrayList<>(sentences.length);
        for (String sentence : sentences) {
            sentence = sentence.trim();
            if (sentence.length() > 0) {
                sentenceList.add(sentence + ".");
            }
        }
        SENTENCES = Collections.unmodifiableList(sentenceList);
    }

    private static final Random r = new Random();
    private static int idProfile = 0;
    private static Date birthDay = null;

    public static void main(String[] args) throws IOException, SQLException {
        LOGGER.debug("зашли в main");
        clearMedia();
        List<Certificate> certificates = loadCertificates();
        List<Profile> profiles = loadProfiles();
        List<ProfileConfig> profileConfigs = getProfileConfigs();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            LOGGER.debug("подключились к базе данных");
            connection.setAutoCommit(false);
            clearDB(connection);
            for (Profile p : profiles) {
                ProfileConfig profileConfig = profileConfigs.get(r.nextInt(profileConfigs.size()));
                createProfile(connection, p, profileConfig, certificates);
                LOGGER.debug("Created profile for {} {}", p.firstName, p.lastName);
            }
            insertSkillCategories(connection);
            connection.commit();
        }
        LOGGER.debug("вышли из main");
        LOGGER.info("Environment is ready");
    }

    private static void insertSkillCategories(Connection connection) throws SQLException {
        int id =1;
        Map<String, Set<String>> categories = createSkillMap();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into skill_category values (?, ?)");
        for (String category: categories.keySet() ){
            preparedStatement.setLong(1,id++);
            preparedStatement.setString(2, category);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    private static void clearMedia() throws IOException {
        LOGGER.debug("зашли в clearMedia");
        if (Files.exists(Paths.get(MEDIA_DIR))) {
            Files.walkFileTree(Paths.get(MEDIA_DIR), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    LOGGER.debug("зашли в clearMedia удаление файлов");
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    LOGGER.debug("зашли в clearMedia удаление папки");
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        File file = new File(MEDIA_DIR);
        if (!file.exists()) {
            if (!file.mkdir()) {LOGGER.debug("зашли в clearMedia не удалось создание папки");} else {
            LOGGER.debug("зашли в clearMedia создание папки");}
        }
        LOGGER.debug("вышли из clearMedia");
        LOGGER.info("Media dir cleared");
    }

    // чистим базу данных
    private static void clearDB(Connection connection) throws SQLException {
        LOGGER.debug("зашли в clearDB");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from profile");
            statement.executeUpdate("delete from profile");
            statement.executeUpdate("delete from skill_category");
            statement.executeQuery("select setval('profile_seq', 1, false)");
            statement.executeQuery("select setval('hobby_seq', 1, false)");
            statement.executeQuery("select setval('certificate_seq', 1, false)");
            statement.executeQuery("select setval('education_seq', 1, false)");
            statement.executeQuery("select setval('language_seq', 1, false)");
            statement.executeQuery("select setval('practic_seq', 1, false)");
            statement.executeQuery("select setval('skill_seq', 1, false)");
            statement.executeQuery("select setval('course_seq', 1, false)");
            LOGGER.info("Db cleared");
        }
        LOGGER.debug("вышли из clearDB");
    }

    private static void createProfile(Connection connection, Profile profile, ProfileConfig profileConfig, List<Certificate> certificates) throws SQLException, IOException {
        LOGGER.debug("зашли в createProfile");
        insertProfileData(connection, profile, profileConfig);
        if (profileConfig.certificates > 0) {
            insertCertificates(connection, profileConfig.certificates, certificates);
        }
        insertEducation(connection);
        insertHobbies(connection);
        insertPractics(connection, profileConfig);
        insertSkill(connection, profileConfig);
        insertCourses(connection);
        insertLanguages(connection);

        LOGGER.debug("вышли из createProfile");

    }

    private static void insertLanguages(Connection connection) throws SQLException {
        List<String> languages = new ArrayList<>();
        languages.add("English");
        if (r.nextBoolean()) {
            int cnt = r.nextInt(1) + 1;
            List<String> otherLng = new ArrayList<>(Arrays.asList(FOREGIN_LANGUAGES));
            Collections.shuffle(otherLng);
            for (int i = 0; i < cnt; i++) {
                languages.add(otherLng.remove(0));
            }
        }
        PreparedStatement ps = connection.prepareStatement("insert into language values (nextval('language_seq'),?,?,?,?)");
        try {
            for (String language : languages) {
                LanguageType langType = languageTypes.get(r.nextInt(languageTypes.size()));
                LanguageLevel langLevel = languageLevels.get(r.nextInt(languageLevels.size()));
                ps.setLong(1, idProfile);
                ps.setString(2, language);
                ps.setString(3, langLevel.getDbValue());
                ps.setString(4, langType.getDbValue());
                ps.addBatch();
                if (langType != LanguageType.ALL) {
                    ps.setLong(1, idProfile);
                    ps.setString(2, language);
                    LanguageLevel newLangLevel = languageLevels.get(r.nextInt(languageLevels.size()));
                    while (newLangLevel == langLevel) {
                        newLangLevel = languageLevels.get(r.nextInt(languageLevels.size()));
                    }
                    ps.setString(3, langLevel.getDbValue());
                    ps.setString(4, langType.getReverseType().getDbValue());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } finally {


            ps.close();
        }
    }

    private static void insertCourses(Connection connection) throws SQLException {
        if (r.nextBoolean()) {
            PreparedStatement ps = connection.prepareStatement("insert into course values (nextval('course_seq'),?,?,?,?)");
            try {
                ps.setLong(1, idProfile);
                ps.setString(2, "Java Advanced Course");
                ps.setString(3, "SourceIt");
                Date finish = randomFinishEducation();
                if (finish.getTime() > System.currentTimeMillis()) {
                    ps.setNull(4, Types.DATE);
                } else {
                    ps.setDate(4, finish);
                }
                ps.executeUpdate();
            } finally {
                ps.close();
            }
        }
    }

    private static void insertSkill(Connection connection, ProfileConfig profileConfig) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into skill values (nextval('skill_seq'),?,?,?)");
        try {
            Map<String, Set<String>> map = createSkillMap();

            for (Course course : profileConfig.courses) {
                for (String key : map.keySet()) {
                    map.get(key).addAll(course.skills.get(key));
                }
            }
            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    ps.setLong(1, idProfile);
                    ps.setString(2, entry.getKey());
                    ps.setString(3, StringUtils.join(entry.getValue().toArray(), ","));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } finally {
            ps.close();
        }
    }

    private static void insertPractics(Connection connection, ProfileConfig profileConfig) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into practic values (nextval('practic_seq'),?,?,?,?,?,?,?,?)");
        try {
            boolean currentCourse = r.nextBoolean();
            Date finish = addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -(r.nextInt(3) + 1), false);
            for (Course course : profileConfig.courses) {
                ps.setLong(1, idProfile);
                ps.setString(2, course.name);
                ps.setString(3, course.company);
                if (currentCourse) {
                    ps.setDate(4, addField(new Date(System.currentTimeMillis()), Calendar.MONTH, -1, false));
                    ps.setNull(5, Types.DATE);
                } else {
                    ps.setDate(4, addField(finish, Calendar.MONTH, -1, false));
                    ps.setDate(5, finish);
                    finish = addField(finish, Calendar.MONTH, -(r.nextInt(3) + 1), false);
                }
                ps.setString(6, course.responsibilities);
                if (course.demo == null) {
                    ps.setNull(7, Types.VARCHAR);
                } else {
                    ps.setString(7, course.demo);
                }
                if (course.github == null) {
                    ps.setNull(8, Types.VARCHAR);
                } else {
                    ps.setString(8, course.github);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        } finally {
            ps.close();
        }
    }

    private static void insertHobbies(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into hobby values (nextval('hobby_seq'),?,?)");
        try {
            List<String> hobbies = new ArrayList<>(Arrays.asList(HOBBIES));
            Collections.shuffle(hobbies);
            for (int i = 0; i < 5; i++) {
                if (r.nextBoolean()) {
                    statement.setLong(1, idProfile);
                    statement.setString(2, hobbies.get(i));
                    statement.addBatch();
                }
            }
            statement.executeBatch();
        } finally {
            statement.close();
        }
    }

    private static void insertEducation(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into education values (nextval('education_seq'),?,?,?,?,?,?)");
        try {

            statement.setLong(1, idProfile);
            statement.setString(2, "The specialist degree in Electronic Engineering");
            Date finishDate = randomFinishEducation();
            statement.setInt(3, new DateTime(addField(finishDate, Calendar.YEAR, -4, true)).getYear());
            if (finishDate.getTime() > System.currentTimeMillis()) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, new DateTime(finishDate).getYear());
            }
            statement.setString(5, "BSUIR");
            statement.setString(6, "Computer Science");
            statement.executeUpdate();
        } finally {
            statement.close();
        }
    }

    private static Date addField(Date finish, int field, int value, boolean isBeginEducation) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(finish.getTime());
        cl.add(field, value);
        if (isBeginEducation) {
            cl.set(Calendar.DAY_OF_MONTH, 1);
            cl.set(Calendar.MONTH, Calendar.SEPTEMBER);
        }
        return new Date(cl.getTimeInMillis());

    }

    private static Date randomFinishEducation() {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(birthDay.getTime());
        cl.set(Calendar.DAY_OF_MONTH, 30);
        cl.set(Calendar.MONTH, Calendar.JUNE);
        int year = cl.get(Calendar.YEAR) + 21;
        cl.set(Calendar.YEAR, year + r.nextInt(3));
        return new Date(cl.getTimeInMillis());
    }

    private static void insertCertificates(Connection connection, int certificateCount, List<Certificate> certificates) throws SQLException, IOException {
        Collections.shuffle(certificates);
        PreparedStatement statement = connection.prepareStatement("insert into certificate values (nextval('certificate_seq'),?,?,?,?)");
        try {
            for (int i = 1; i < certificateCount && i < certificates.size(); i++) {
                Certificate certificate = certificates.get(i);
                statement.setLong(1, idProfile);
                statement.setString(2, certificate.name);
                String uid = UUID.randomUUID().toString() + ".jpg";
                File img = new File(MEDIA_DIR + "/certificates/" + uid);
                if (!img.getParentFile().exists()) {
                    img.getParentFile().mkdir();
                }
                Files.copy(Paths.get(certificate.largeImg), Paths.get(img.getAbsolutePath()));
                statement.setString(3, "/media/certificates/" + uid);
                String smallUid = uid.replace(".jpg", "-sm.jpg");
                Thumbnails.of(img).size(100, 100).toFile(new File(MEDIA_DIR + "/certificates/" + smallUid));
                statement.setString(4, "/media/certificates/" + smallUid);
                statement.addBatch();
            }
            statement.executeBatch();
        } finally {
            statement.close();
        }

    }

    private static void insertProfileData(Connection connection, Profile profile, ProfileConfig profileConfig) throws SQLException, IOException {
        PreparedStatement statement = connection.prepareStatement("insert into profile values (nextval('profile_seq'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,true,?,?,?,?,?,?,?)");
        try {
            statement.setString(1, (profile.firstName + "-" + profile.lastName).toLowerCase());
            statement.setString(2, profile.firstName);
            statement.setString(3, profile.lastName);
            birthDay = randomBirthDay();
            statement.setDate(4, birthDay);
            statement.setString(5, generatePhoneNumber());
            statement.setString(6, (profile.firstName + '-' + profile.lastName).toLowerCase() + "@gmail.com");
            statement.setString(7, COUNTRY[r.nextInt(COUNTRY.length)]);
            statement.setString(8, CITIES[r.nextInt(CITIES.length)]);
            statement.setString(9, profileConfig.objective);
            statement.setString(10, profileConfig.summary);
            String uid = UUID.randomUUID().toString() + ".jpg";
            File photo = new File(MEDIA_DIR + "/avatar/" + uid);
            if (!photo.getParentFile().exists()) {
                photo.getParentFile().mkdir();
            }
            Files.copy(Paths.get(profile.photo), Paths.get(photo.getAbsolutePath()));
            statement.setString(11, "/media/avatar/" + uid);
            String smallUid = uid.replace(".jpg", "-sm.jpg");
            Thumbnails.of(photo).size(100, 100).toFile(new File(MEDIA_DIR + "/avatar/" + smallUid));
            statement.setString(12, "/media/avatar/" + smallUid);
            if (r.nextBoolean()) {
                statement.setString(13, getInfo());
            } else {
                statement.setNull(13, Types.VARCHAR);
            }
            statement.setString(14, PASSWORD_HASH);

            statement.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
            if (r.nextBoolean()) {
                statement.setString(16, (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(16, Types.VARCHAR);
            }
            if (r.nextBoolean()) {
                statement.setString(17, "https://vk.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(17, Types.VARCHAR);
            }
            if (r.nextBoolean()) {
                statement.setString(18, "https://facebook.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(18, Types.VARCHAR);
            }
            if (r.nextBoolean()) {
                statement.setString(19, "https://linkedin.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(19, Types.VARCHAR);
            }
            if (r.nextBoolean()) {
                statement.setString(20, "https://github.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(20, Types.VARCHAR);
            }
            if (r.nextBoolean()) {
                statement.setString(21, "https://stackoverflow.com/" + (profile.firstName + "-" + profile.lastName).toLowerCase());
            } else {
                statement.setNull(21, Types.VARCHAR);
            }

            statement.executeUpdate();
        } finally {
            statement.close();
        }
        idProfile++;

    }

    private static String getInfo() {
        int endIndex = r.nextInt(SENTENCES.size());
        int startIndex = 0;
        if (endIndex > 0) {
            startIndex = r.nextInt(endIndex);
        }
        if (endIndex - startIndex > 4) {
            endIndex = startIndex + 3;
        }
        return StringUtils.join(SENTENCES.subList(startIndex, endIndex), " ");
    }

    private static String generatePhoneNumber() {
        StringBuilder phone = new StringBuilder("+37529");
        for (int i = 0; i < 7; i++) {
            int code = '1' + r.nextInt(9);
            phone.append(((char) code));
        }
        return phone.toString();
    }

    private static Date randomBirthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, r.nextInt(30));
        calendar.set(Calendar.MONTH, r.nextInt(12));
        int year = calendar.get(Calendar.YEAR) - 30;
        calendar.set(Calendar.YEAR, year + r.nextInt(10));
        return new Date(calendar.getTimeInMillis());
    }

    private static List<Certificate> loadCertificates() {
        LOGGER.debug("зашли в loadCertificates");
        File[] files = new File(CERTIFICATES_PATH).listFiles();
        List<Certificate> list = new ArrayList<>(files.length);
        for (File f : files) {
            String name = f.getName().replace("-", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            list.add(new Certificate(name, f.getAbsolutePath()));
        }
        LOGGER.debug("вышли из loadCertificates");
        return list;
    }

    private static List<Profile> loadProfiles() {
        LOGGER.debug("зашли в loadProfiles");
        File[] photos = new File(PHOTO_PATH).listFiles();
        List<Profile> list = new ArrayList<>(photos.length);
        for (File f : photos) {
            String[] names = f.getName().replace(".jpg", "").split(" ");
            list.add(new Profile(names[0], names[1], f.getAbsolutePath()));
        }
        Collections.sort(list, new Comparator<Profile>() {
            @Override
            public int compare(Profile o1, Profile o2) {
                int firstNameCompare = o1.firstName.compareTo(o2.firstName);
                if (firstNameCompare != 0) {
                    return firstNameCompare;
                } else {
                    return o1.lastName.compareTo(o2.lastName);
                }
            }
        });
        LOGGER.debug("вышли из loadProfiles");
        return list;
    }


    private static Map<String, Set<String>> createSkillMap() {
        LOGGER.debug("зашли в createSkillMap");
        Map<String, Set<String>> skills = new LinkedHashMap<>();
        skills.put(LANGUAGES, new LinkedHashSet<String>());
        skills.put("DBMS", new LinkedHashSet<String>());
        skills.put("Web", new LinkedHashSet<String>());
        skills.put("Java", new LinkedHashSet<String>());
        skills.put("IDE", new LinkedHashSet<String>());
        skills.put("CVS", new LinkedHashSet<String>());
        skills.put(WEB_SERVERS, new LinkedHashSet<String>());
        skills.put(BUILD_SYSTEM, new LinkedHashSet<String>());
        skills.put(CLOUD, new LinkedHashSet<String>());
        LOGGER.debug("вышли из createSkillMap");
        return skills;
    }

    private static List<ProfileConfig> getProfileConfigs() {
        LOGGER.debug("зашли в getProfileConfigs");
        List<ProfileConfig> res = new ArrayList<>();
        res.add(new ProfileConfig("Junior java trainee position", "Java core course with developing one simple console application", new Course[]{Course.createCoreCourse()}, 0));
        res.add(new ProfileConfig("Junior java trainee position", "One Java professional course with developing web application blog (Link to demo is provided)",
                new Course[]{Course.createBaseCourse()}, 0));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, "One Java professional course with developing web application resume (Link to demo is provided)",
                new Course[]{Course.createAdvancedCourse()}, 0));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, "One Java professional course with developing web application resume (Link to demo is provided)",
                new Course[]{Course.createAdvancedCourse()}, 1));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, TWO_YEAR_JAVA_DEVELOPER_POSITION,
                new Course[]{Course.createAdvancedCourse(), Course.createBaseCourse()}, 1));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, TWO_YEAR_JAVA_DEVELOPER_POSITION,
                new Course[]{Course.createAdvancedCourse(), Course.createBaseCourse()}, 1));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, TWO_YEAR_JAVA_DEVELOPER_POSITION,
                new Course[]{Course.createAdvancedCourse(), Course.createBaseCourse()}, 1));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION, TWO_YEAR_JAVA_DEVELOPER_POSITION,
                new Course[]{Course.createAdvancedCourse(), Course.createBaseCourse()}, 2));
        res.add(new ProfileConfig(JUNIOR_JAVA_DEVELOPER_POSITION,
                "Three Java professional courses with developing one console application and two web applications: blog and resume (Links to demo are provided)",
                new Course[]{Course.createAdvancedCourse(), Course.createBaseCourse(), Course.createCoreCourse()}, 2));
        LOGGER.debug("вышли из getProfileConfigs");
        return res;
    }


    private static final class Certificate {
        private final String name;
        private final String largeImg;

        public Certificate(String name, String largeImg) {
            super();
            this.name = name;
            this.largeImg = largeImg;
        }
    }

    private static final class Profile {
        private final String firstName;
        private final String lastName;
        private final String photo;

        public Profile(String firstName, String lastName, String photo) {
            super();
            this.firstName = firstName;
            this.lastName = lastName;
            this.photo = photo;
        }

        @Override
        public String toString() {
            return String.format("Profile [firstName=%s, lastName=%s]", firstName, lastName);
        }
    }

    private static final class ProfileConfig {
        private final String objective;
        private final String summary;
        private final Course[] courses;
        private final int certificates;

        public ProfileConfig(String objective, String summary, Course[] courses, int certificates) {
            this.objective = objective;
            this.summary = summary;
            this.courses = courses;
            this.certificates = certificates;
        }
    }

    private static final class Course {
        private final String name;
        private final String company;
        private final String github;
        private final String responsibilities;
        private final String demo;
        private final Map<String, Set<String>> skills;

        public Course(String name, String company, String github, String responsibilities, String demo, Map<String, Set<String>> skills) {
            super();
            this.name = name;
            this.company = company;
            this.github = github;
            this.responsibilities = responsibilities;
            this.demo = demo;
            this.skills = skills;
        }

        static Course createCoreCourse() {
            Map<String, Set<String>> skills = createSkillMap();
            skills.get(LANGUAGES).add("Java");

            skills.get("DBMS").add("Mysql");

            skills.get("Java").add("Threads");
            skills.get("Java").add("IO");
            skills.get("Java").add("JAXB");
            skills.get("Java").add("GSON");

            skills.get("IDE").add(ECLIPSE_FOR_JEE_DEVELOPER);

            skills.get("CVS").add("Git");
            skills.get("CVS").add(GITHUB);

            skills.get(BUILD_SYSTEM).add(MAVEN);

            return new Course("Java Core Course", DEV_STUDY_NET, null, "Developing the java console application which imports XML, JSON, Properties, CVS to Db via JDBC", null, skills);
        }

        static Course createBaseCourse() {
            Map<String, Set<String>> skills = createSkillMap();
            skills.get(LANGUAGES).add("Java");
            skills.get(LANGUAGES).add("SQL");

            skills.get("DBMS").add("Postgresql");

            skills.get("Web").add("HTML");
            skills.get("Web").add("CSS");
            skills.get("Web").add("JS");
            skills.get("Web").add("JS");
            skills.get("Web").add("Foundation");
            skills.get("Web").add("JQuery");

            skills.get("Java").add("Servlets");
            skills.get("Java").add("Logback");
            skills.get("Java").add("JSP");
            skills.get("Java").add("JSTL");
            skills.get("Java").add("JDBC");
            skills.get("Java").add("Apache Commons");
            skills.get("Java").add("Google+ Social API");

            skills.get("IDE").add(ECLIPSE_FOR_JEE_DEVELOPER);

            skills.get("CVS").add("Git");
            skills.get("CVS").add(GITHUB);

            skills.get(WEB_SERVERS).add("Tomcat");

            skills.get(BUILD_SYSTEM).add(MAVEN);

            skills.get(CLOUD).add("OpenShift");

            return new Course("Java Base Course", DEV_STUDY_NET, "https://github.com/TODO",
                    "Developing the web application 'blog' using free HTML template, downloaded from intenet. Populating database by test data and uploading web project to OpenShift free hosting",
                    "http://LINK_TO_DEMO_SITE", skills);
        }

        static Course createAdvancedCourse() {
            Map<String, Set<String>> skills = createSkillMap();
            skills.get(LANGUAGES).add("Java");
            skills.get(LANGUAGES).add("SQL");
            skills.get(LANGUAGES).add("PLSQL");

            skills.get("DBMS").add("Postgresql");

            skills.get("Web").add("HTML");
            skills.get("Web").add("CSS");
            skills.get("Web").add("JS");
            skills.get("Web").add("JS");
            skills.get("Web").add("Bootstrap");
            skills.get("Web").add("JQuery");

            skills.get("Java").add("Spring MVC");
            skills.get("Java").add("Logback");
            skills.get("Java").add("JSP");
            skills.get("Java").add("JSTL");
            skills.get("Java").add("Spring Data JPA");
            skills.get("Java").add("Apache Commons");
            skills.get("Java").add("Spring Security");
            skills.get("Java").add("Hibernate JPA");
            skills.get("Java").add("Facebook Social API");

            skills.get("IDE").add(ECLIPSE_FOR_JEE_DEVELOPER);

            skills.get("CVS").add("Git");
            skills.get("CVS").add(GITHUB);

            skills.get(WEB_SERVERS).add("Tomcat");
            skills.get(WEB_SERVERS).add("Nginx");

            skills.get(BUILD_SYSTEM).add(MAVEN);

            skills.get(CLOUD).add("AWS");

            return new Course("Java Advanced Course", DEV_STUDY_NET, "https://github.com/TODO",
                    "Developing the web application 'online-resume' using bootstrap HTML template, downloaded from intenet. Populating database by test data and uploading web project to AWS EC2 instance",
                    "http://LINK_TO_DEMO_SITE", skills);
        }


    }


}

