
package com.example.book_management.service;

import com.example.book_management.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public String generateSequence(String seqName) {
        // Attempt to find and increment the sequence
        DatabaseSequence counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                DatabaseSequence.class
        );

        if (counter == null) {
            DatabaseSequence newCounter = new DatabaseSequence();
            newCounter.setId(seqName);
            newCounter.setSeq(1);  // Start the sequence from 1
            mongoOperations.save(newCounter);  // Save the new counter
            return String.format("B-%03d", 1);  // Return the first ID in the correct format
        }


        long seqId = counter.getSeq(); // Get the incremented sequence
        return String.format("B-%03d", seqId);  // Format as B-001, B-002, etc.

    }
}


