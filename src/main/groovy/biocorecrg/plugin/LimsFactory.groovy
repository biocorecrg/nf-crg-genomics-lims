package biocorecrg.plugin

import nextflow.Session
import nextflow.trace.TraceObserver
import nextflow.trace.TraceObserverFactory
import org.pf4j.Extension

@Extension
class LimsFactory implements TraceObserverFactory {
    @Override
    Collection<TraceObserver> create(Session session) {
        def config = session.config.lims as Map ?: [:]
        def enabled = config.containsKey('enabled') ? config.enabled : false
        def params = session.binding.getVariable('params') as Map ?: [:]
        if (params.containsKey('lims_enabled')) {
            enabled = params.lims_enabled
        }
        if (enabled == false || enabled?.toString()?.toLowerCase() == 'false') {
            return []
        }
        return [new LimsObserver(session)]
    }
}
