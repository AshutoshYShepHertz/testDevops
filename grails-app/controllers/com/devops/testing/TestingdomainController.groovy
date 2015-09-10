package com.devops.testing

import org.springframework.dao.DataIntegrityViolationException

class TestingdomainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [testingdomainInstanceList: Testingdomain.list(params), testingdomainInstanceTotal: Testingdomain.count()]
    }

    def create() {
        [testingdomainInstance: new Testingdomain(params)]
    }

    def save() {
        def testingdomainInstance = new Testingdomain(params)
        if (!testingdomainInstance.save(flush: true)) {
            render(view: "create", model: [testingdomainInstance: testingdomainInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), testingdomainInstance.id])
        redirect(action: "show", id: testingdomainInstance.id)
    }

    def show(Long id) {
        def testingdomainInstance = Testingdomain.get(id)
        if (!testingdomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "list")
            return
        }

        [testingdomainInstance: testingdomainInstance]
    }

    def edit(Long id) {
        def testingdomainInstance = Testingdomain.get(id)
        if (!testingdomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "list")
            return
        }

        [testingdomainInstance: testingdomainInstance]
    }

    def update(Long id, Long version) {
        def testingdomainInstance = Testingdomain.get(id)
        if (!testingdomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (testingdomainInstance.version > version) {
                testingdomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'testingdomain.label', default: 'Testingdomain')] as Object[],
                          "Another user has updated this Testingdomain while you were editing")
                render(view: "edit", model: [testingdomainInstance: testingdomainInstance])
                return
            }
        }

        testingdomainInstance.properties = params

        if (!testingdomainInstance.save(flush: true)) {
            render(view: "edit", model: [testingdomainInstance: testingdomainInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), testingdomainInstance.id])
        redirect(action: "show", id: testingdomainInstance.id)
    }

    def delete(Long id) {
        def testingdomainInstance = Testingdomain.get(id)
        if (!testingdomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "list")
            return
        }

        try {
            testingdomainInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'testingdomain.label', default: 'Testingdomain'), id])
            redirect(action: "show", id: id)
        }
    }
}
