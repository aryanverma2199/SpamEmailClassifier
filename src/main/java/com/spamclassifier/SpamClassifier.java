package com.spamclassifier;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SpamClassifier {
    private ClassifierUtils classifierUtils;
    private Instances data;

    public SpamClassifier() throws Exception {
        DataLoader dataLoader = new DataLoader();
        data = new DataSource("src/main/resources/datasets/spamassassin.arff").getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        classifierUtils = new ClassifierUtils();
        classifierUtils.trainModel(data);
    }

    public String classifyEmail(String emailContent) throws Exception {
        return classifierUtils.classifyEmail(emailContent, data);
    }

    public static void main(String[] args) {
        try {
            SpamClassifier spamClassifier = new SpamClassifier();
            String[] testEmails = {
                "Congratulations! You've won a $1,000 Walmart gift card. Click here to claim now.",
                "This is a reminder for your appointment tomorrow at 10 AM.",
                "Dear user, your account has been compromised. Please verify your identity.",
                "Don't forget to bring your documents for the meeting next week.",
                "Limited time offer! Buy one get one free on all items!",
                "Hi, can we reschedule our meeting for next week?",
                "Your invoice for the last month is attached. Please review it.",
                "Claim your free vacation now! Click here for details.",
                "Thanks for signing up for our newsletter!",
                "Urgent: Update your payment information immediately!"
            };

            for (String email : testEmails) {
                String result = spamClassifier.classifyEmail(email);
                System.out.println("Email: \"" + email + "\" - Classification: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
