package tv.twitch.android.shared.experiments;


import tv.twitch.android.mod.bridges.Hooks;


public class ExperimentHelper {
    public boolean isInOnGroupForBinaryExperiment(Experiment experiment) { // TODO: __INJECT_METHOD
        return Hooks.hookExperimental(experiment, org(experiment));
    }

    public boolean org(Experiment experiment) { // TODO: __RENAME__isInOnGroupForBinaryExperiment
        return false;
    }
}
