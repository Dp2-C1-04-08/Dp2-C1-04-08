
package acme.features.assistant.dashboard;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.ToDoubleFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Nature;
import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.forms.AssistantDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Controller
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AssistantDashboard dashboard;
		final HashMap<Nature, Integer> tutorialCountByCourseType = new HashMap();
		tutorialCountByCourseType.put(Nature.THEORETICAL, 0);
		tutorialCountByCourseType.put(Nature.HANDS_ON, 0);
		Collection<Tutorial> tutorials;
		Collection<SessionTutorial> sessions;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		final int assistantId = principal.getActiveRoleId();
		tutorials = this.repository.findTutorialsByAssistantId(assistantId);
		sessions = this.repository.findSessionTutorialByAssistantId(assistantId);

		for (final Tutorial t : tutorials) {
			final Nature type = t.getCourse().getCourseType();
			Integer count = tutorialCountByCourseType.getOrDefault(type, 0);
			count += 1;
			tutorialCountByCourseType.put(type, count);
		}
		Double tutorialsAverage;
		Double tutorialsDeviation;
		Double tutorialsMinimum;
		Double tutorialsMaximum;
		Double sessionsAverage;
		Double sessionsDeviation;
		Double sessionsMinimum;
		Double sessionsMaximum;

		//tutorials calculations
		if (tutorials.isEmpty()) {
			tutorialsAverage = 0.;
			tutorialsDeviation = 0.;
			tutorialsMinimum = 0.;
			tutorialsMaximum = 0.;
		} else {
			tutorialsAverage = this.repository.averageDurationTutorial(assistantId);
			tutorialsDeviation = this.repository.deviationDuractionTutorial(assistantId);
			tutorialsMinimum = this.repository.minDurationTutorial(assistantId);
			tutorialsMaximum = this.repository.maxDurationTutorial(assistantId);
		}
		//sessions calculations
		if (sessions.isEmpty()) {
			sessionsAverage = 0.;
			sessionsDeviation = 0.;
			sessionsMinimum = 0.;
			sessionsMaximum = 0.;
		} else {
			final ToDoubleFunction<SessionTutorial> getDuration = st -> {
				final Duration objectDuration = MomentHelper.computeDuration(st.getStartTime(), st.getEndTime());
				return objectDuration.getSeconds() / 3600.;
			};
			sessionsAverage = sessions.stream().mapToDouble(getDuration).average().getAsDouble();
			sessionsMinimum = sessions.stream().mapToDouble(getDuration).min().getAsDouble();
			sessionsMaximum = sessions.stream().mapToDouble(getDuration).max().getAsDouble();
			final double sumOfSquaredDifferences = sessions.stream().mapToDouble(getDuration).map(datum -> Math.pow(datum - sessionsAverage, 2)).sum();
			sessionsDeviation = Math.sqrt(sumOfSquaredDifferences / sessions.size());
		}

		dashboard = new AssistantDashboard();
		dashboard.setTutorialCountByCourseType(tutorialCountByCourseType);

		dashboard.setTutorialsAverage(tutorialsAverage);
		dashboard.setTutorialsDeviation(tutorialsDeviation);
		dashboard.setTutorialsMinimum(tutorialsMinimum);
		dashboard.setTutorialsMaximum(tutorialsMaximum);

		dashboard.setSessionsAverage(sessionsAverage);
		dashboard.setSessionsDeviation(sessionsDeviation);
		dashboard.setSessionsMinimum(sessionsMinimum);
		dashboard.setSessionsMaximum(sessionsMaximum);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple = null;

		tuple = super.unbind(object,  //
			"tutorialsAverage", "tutorialsDeviation", "tutorialsMinimum", "tutorialsMaximum", //
			"sessionsAverage", "sessionsDeviation", "sessionsMinimum", "sessionsMaximum");

		tuple.put("handsOnTutorialCount", object.getTutorialCountByCourseType().get(Nature.HANDS_ON));
		tuple.put("theoreticalTutorialCount", object.getTutorialCountByCourseType().get(Nature.THEORETICAL));
		super.getResponse().setData(tuple);
	}

}
