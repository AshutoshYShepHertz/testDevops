package com.devops.testing



import org.junit.*
import grails.test.mixin.*

@TestFor(TestingdomainController)
@Mock(Testingdomain)
class TestingdomainControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/testingdomain/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.testingdomainInstanceList.size() == 0
        assert model.testingdomainInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.testingdomainInstance != null
    }

    void testSave() {
        controller.save()

        assert model.testingdomainInstance != null
        assert view == '/testingdomain/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/testingdomain/show/1'
        assert controller.flash.message != null
        assert Testingdomain.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/testingdomain/list'

        populateValidParams(params)
        def testingdomain = new Testingdomain(params)

        assert testingdomain.save() != null

        params.id = testingdomain.id

        def model = controller.show()

        assert model.testingdomainInstance == testingdomain
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/testingdomain/list'

        populateValidParams(params)
        def testingdomain = new Testingdomain(params)

        assert testingdomain.save() != null

        params.id = testingdomain.id

        def model = controller.edit()

        assert model.testingdomainInstance == testingdomain
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/testingdomain/list'

        response.reset()

        populateValidParams(params)
        def testingdomain = new Testingdomain(params)

        assert testingdomain.save() != null

        // test invalid parameters in update
        params.id = testingdomain.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/testingdomain/edit"
        assert model.testingdomainInstance != null

        testingdomain.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/testingdomain/show/$testingdomain.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        testingdomain.clearErrors()

        populateValidParams(params)
        params.id = testingdomain.id
        params.version = -1
        controller.update()

        assert view == "/testingdomain/edit"
        assert model.testingdomainInstance != null
        assert model.testingdomainInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/testingdomain/list'

        response.reset()

        populateValidParams(params)
        def testingdomain = new Testingdomain(params)

        assert testingdomain.save() != null
        assert Testingdomain.count() == 1

        params.id = testingdomain.id

        controller.delete()

        assert Testingdomain.count() == 0
        assert Testingdomain.get(testingdomain.id) == null
        assert response.redirectedUrl == '/testingdomain/list'
    }
}
