package com.learning.trend.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tribuo.*;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.evaluation.LabelEvaluation;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.columnar.FieldProcessor;
import org.tribuo.data.columnar.RowProcessor;
import org.tribuo.data.columnar.processors.field.DoubleFieldProcessor;
import org.tribuo.data.columnar.processors.field.IdentityProcessor;
import org.tribuo.data.columnar.processors.response.FieldResponseProcessor;
import org.tribuo.data.csv.CSVDataSource;
import org.tribuo.evaluation.TrainTestSplitter;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class FarmerInputModelTrainer {

    private static final Logger LOG = LoggerFactory.getLogger(FarmerInputModelTrainer.class);

    public static final String DATASET_PATH = "src/main/resources/dataset/farmer_input_timeseries_records.csv";

    public static final String MODEL_PATH = "src/main/resources/model/farmer_input_timeseries.ser";

    public static DataSource<Label> loadDataSource(String path) throws Exception {
        LabelFactory labelFactory = new LabelFactory();

        // ✅ REQUIRED: Map<String, FieldProcessor>
        Map<String, FieldProcessor> fieldProcessorMap = new HashMap<>();

        // Categorical
        fieldProcessorMap.put("crop_type", new IdentityProcessor("crop_type"));
        fieldProcessorMap.put("soil_type", new IdentityProcessor("soil_type"));
        fieldProcessorMap.put("region", new IdentityProcessor("region"));
        fieldProcessorMap.put("season", new IdentityProcessor("season"));

        // Numeric
        fieldProcessorMap.put("year", new DoubleFieldProcessor("year"));
        fieldProcessorMap.put("month", new DoubleFieldProcessor("month"));
        fieldProcessorMap.put("rainfall_mm", new DoubleFieldProcessor("rainfall_mm"));
        fieldProcessorMap.put("farmer_land_size_acres", new DoubleFieldProcessor("farmer_land_size_acres"));

        // ✅ Response processor (explicit generic to avoid inference issue)
        FieldResponseProcessor<Label> responseProcessor =
                new FieldResponseProcessor<Label>("previous_input_usage", "UNKNOWN", labelFactory);

        // ✅ THIS matches your constructor exactly
        RowProcessor<Label> rowProcessor = new RowProcessor<>(
                responseProcessor,
                fieldProcessorMap
        );

        return new CSVDataSource<>(
                Paths.get(path),
                rowProcessor,
                true
        );
    }
    public static LogisticRegressionTrainer createTrainer() {

        return new LogisticRegressionTrainer();
    }

    public static Model<Label> trainAndEvaluate(DataSource<Label> dataSource,
                                                Trainer<Label> trainer) {

        // Split using DataSource
        TrainTestSplitter<Label> splitter = new TrainTestSplitter<>(dataSource, 0.7, 42);

        // Convert to Dataset AFTER split
        Dataset<Label> train = new MutableDataset<>(splitter.getTrain());
        Dataset<Label> test = new MutableDataset<>(splitter.getTest());

        int emptyCount = 0;

        for (Example<Label> ex : new MutableDataset<>(dataSource)) {
            if (ex.getOutput().getLabel().isEmpty()) {
                emptyCount++;
            }
        }

        LOG.info("Empty label rows: " + emptyCount);
        // Train model
        LOG.info("Outputs found: " + train.getOutputs());
        LOG.info("Number of unique outputs: " + train.getOutputInfo().size());
        LOG.info("train.size():" + train.size());

        LOG.info("Unique Labels found: " + train.getOutputIDInfo());
        LOG.info("Number of Labels: " + train.getOutputIDInfo().size());

        LOG.info("Labels model will learn: " + train.getOutputIDInfo().getDomain());
        LOG.info("Labels in test set: " + test.getOutputIDInfo().getDomain());

        // Only train if labels actually exist
        if (train.getOutputIDInfo().size() > 0) {

            Model<Label> model = trainer.train(train);

            // Evaluate
            LabelEvaluator evaluator = new LabelEvaluator();
            LabelEvaluation evaluation = evaluator.evaluate(model, test);

            LOG.info("Accuracy: " + evaluation.accuracy());
            LOG.info(evaluation.toString());

            return model;
        }
        else {
            System.err.println("CRITICAL: No labels found! Check your CSV column headers.");
            return null;
        }
    }

    public static void saveModel(Model<?> model, String path) throws Exception {
        LOG.info("Saving model to: " + path);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(model);
        }
    }

    public static void main(String[] args) throws Exception {

        // 1. Load dataset
        DataSource<Label> dataSource = loadDataSource(DATASET_PATH);

        LOG.info("DataSource loaded with outputs: " + dataSource.toString());

        // 2. Trainer
        var trainer = createTrainer();

        // 3. Train + Evaluate
        var model = trainAndEvaluate(dataSource, trainer);

        // 4. Save model
        saveModel(model, MODEL_PATH);

    }

}