package com.appsdeveloperblog.estore.productservice.command;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class EventsReplayController {

    private final EventProcessingConfiguration eventProcessingConfiguration;

    public EventsReplayController(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @PostMapping("/eventProcessor/{processorName}/reset")
    public ResponseEntity<?> replayEvents(@PathVariable String processorName){
        Optional<TrackingEventProcessor> trackingEventProcessing = this.eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);
        if(trackingEventProcessing.isPresent()){
            TrackingEventProcessor trackingEventProcessor = trackingEventProcessing.get();
            trackingEventProcessor.shutDown();
            trackingEventProcessor.resetTokens();
            trackingEventProcessor.start();
            return ResponseEntity.ok().body(String.format("The event processing name [%s] has been reset", processorName));
        }
        else{
            return ResponseEntity.badRequest().body(String.format("The event processing name [%s] is not tracking processor ", processorName));
        }
    }
}
