package com.spamclassifier;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ClassifierUtils {
    private Classifier classifier;
    private StringToWordVector filter;

    public ClassifierUtils() throws Exception {
        classifier = new NaiveBayes(); // Change to LogisticRegression if needed
        
        // Initialize and configure the StringToWordVector filter
        filter = new StringToWordVector();
        filter.setLowerCaseTokens(true); // Convert tokens to lowercase
    }

    public void trainModel(Instances trainingData) throws Exception {
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        // Apply the filter to the training data
        filter.setInputFormat(trainingData);
        Instances filteredData = Filter.useFilter(trainingData, filter);
        
        classifier.buildClassifier(filteredData); // Train on filtered data
    }

    public String classifyEmail(String emailContent, Instances data) throws Exception {
        // Create a new instance for classification
        Instance instance = new DenseInstance(data.numAttributes());
        instance.setValue(data.attribute(0), emailContent); // Assuming first attribute is content
        
        // Set the dataset for the instance
        instance.setDataset(data); 
        
        // Create a new Instances object with just one instance
        Instances singleInstance = new Instances(data, 0);
        singleInstance.add(instance); // Add the instance to the new Instances object
        
        // Apply the filter to the single instance
        Instances filteredInstance = Filter.useFilter(singleInstance, filter);

        // Classify the filtered instance
        double result = classifier.classifyInstance(filteredInstance.firstInstance());
        return data.classAttribute().value((int) result);
    }
}
