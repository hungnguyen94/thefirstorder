package nl.tudelft.thefirstorder.web.websocket;

import nl.tudelft.thefirstorder.security.SecurityUtils;
import nl.tudelft.thefirstorder.web.websocket.dto.ActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.inject.Inject;
import java.security.Principal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static nl.tudelft.thefirstorder.config.WebsocketConfiguration.IP_ADDRESS;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Inject
    SimpMessageSendingOperations messagingTemplate;

    /**
     * Sends user tracking data.
     * @param activityDto Activity data transfer object
     * @param stompHeaderAccessor stomp header accessor
     * @param principal principal
     * @return Activity DTO
     */
    @SubscribeMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDto,
                                    StompHeaderAccessor stompHeaderAccessor,
                                    Principal principal) {
        activityDto.setUserLogin(SecurityUtils.getCurrentUserLogin());
        activityDto.setUserLogin(principal.getName());
        activityDto.setSessionId(stompHeaderAccessor.getSessionId());
        activityDto.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        Instant instant = Instant.ofEpochMilli(Calendar.getInstance().getTimeInMillis());
        activityDto.setTime(dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault())));
        log.debug("Sending user tracking data {}", activityDto);
        return activityDto;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDTO activityDto = new ActivityDTO();
        activityDto.setSessionId(event.getSessionId());
        activityDto.setPage("logout");
        messagingTemplate.convertAndSend("/topic/tracker", activityDto);
    }
}
